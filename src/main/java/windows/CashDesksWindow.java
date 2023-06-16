package windows;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.basic.CashDesk;
import models.basic.Cashier;
import models.basic.Shop;
import util.AlertUtil;
import util.GeneralUtil;
import util.WindowUtil;

import static constants.StringsConstants.*;

public class CashDesksWindow {
    private final Shop shop;
    private final ListView<String> listView;
    private final ObservableList<String> items;

    private final Label cashDeskNumberLabel;
    private final TextField cashDeskNumberField;
    private final Label cashDeskCashierLabel;
    private final ComboBox<String> cashDeskCashierComboBox;

    private final Button submitBtn;

    private Cashier currCashier;


    public CashDesksWindow() {
        this.items = FXCollections.observableArrayList();
        this.listView = WindowUtil.createListView(this.items);
        this.listView.setCellFactory(this::addListViewContextMenu);
        this.shop = Shop.getInstance();

        this.cashDeskNumberLabel = new Label("Desk number:");
        this.cashDeskNumberField = new TextField();
        this.cashDeskCashierLabel = new Label("Cashier:");
        this.submitBtn = new Button();
        this.submitBtn.setText("Add");
        this.submitBtn.setPrefWidth(100);
        this.submitBtn.setOnAction(this::addSubmitBtnHandle);
        this.cashDeskCashierComboBox = new ComboBox<>();
        this.cashDeskCashierComboBox.setPromptText("Cashier");
        this.cashDeskCashierComboBox.setEditable(true);
        this.cashDeskCashierComboBox.focusedProperty().addListener((observable, oldValue, newValue) -> refreshCashierComboBox());
        this.cashDeskCashierComboBox.valueProperty().addListener(this::addCashierComboBoxChangeListener);
        this.refreshCashierComboBox();
        this.refreshList();
    }

    public Node setupCashDesksWindow() {
        GridPane pane = WindowUtil.createGridPane(6, 5);

        pane.add(this.listView, 0, 0, 2, 10);
        pane.add(this.cashDeskNumberLabel, 2, 0);
        pane.add(this.cashDeskNumberField, 3, 0);
        pane.add(this.cashDeskCashierLabel, 2, 1);
        pane.add(this.cashDeskCashierComboBox, 3, 1);
        pane.add(this.submitBtn, 2, 2);

        return pane;
    }

    private void refreshList() {
        items.clear();
        shop.getCashDesks().forEach(c -> items.add(c.toString()));
    }

    private void refreshCashierComboBox() {
        cashDeskCashierComboBox.setItems(
                FXCollections.observableArrayList(
                        shop.getCashiers().stream().map(Cashier::toString).toList()
                ));
    }

    private void addSubmitBtnHandle(ActionEvent event) {
        if (cashDeskNumberField.getText().trim().isEmpty() || !cashDeskNumberField.getText().trim().matches(NUMBER_REGEX)) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }
        CashDesk cashDesk = new CashDesk(
                Integer.parseInt(cashDeskNumberField.getText().trim()),
                this.currCashier
        );
        try {
            shop.addCashDesk(cashDesk);
            refreshList();
        } catch (IllegalArgumentException | NullPointerException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addCashierComboBoxChangeListener(ObservableValue<? extends String> observableValue, String s, String s1) {
        int id = GeneralUtil.returnId(s);
        if (id < 0) return;
        this.currCashier = this.shop.getCashier(new Cashier(id));
    }

    private ListCell<String> addListViewContextMenu(ListView<String> stringListView) {
        ListCell<String> cell = new ListCell<>();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete %s", cell.itemProperty()));
        deleteItem.setOnAction(event -> {
            try {
                int id = GeneralUtil.returnId(cell.itemProperty().toString());
                if (id < 0) return;
                CashDesk cashDesk = new CashDesk(id);
                shop.removeCashDesk(cashDesk);
                refreshList();
            } catch (IndexOutOfBoundsException ex) {
                AlertUtil.createAlert(ERROR_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
        contextMenu.getItems().add(deleteItem);

        cell.textProperty().bind(cell.itemProperty());

        cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
            if (isNowEmpty) {
                cell.setContextMenu(null);
            } else {
                cell.setContextMenu(contextMenu);
            }
        });
        return cell;
    }

}
