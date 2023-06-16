package windows;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.basic.Cashier;
import models.basic.Shop;
import util.AlertUtil;
import util.WindowUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.StringsConstants.*;

public class CashiersWindow {
    private final Shop shop;
    private final ListView<String> listView;
    private final ObservableList<String> items;

    private final Label cashierNameLabel;
    private final TextField cashierNameField;
    private final Label cashierNumberLabel;
    private final TextField cashierNumberField;
    private final Label cashierSalaryLabel;
    private final TextField cashierSalaryField;

    private final Button submitBtn;


    public CashiersWindow() {
        this.items = FXCollections.observableArrayList();
        this.listView = WindowUtil.createListView(this.items);
        this.listView.setCellFactory(this::addListViewContextMenu);
        this.shop = Shop.getInstance();

        this.cashierNameLabel = new Label("Name:");
        this.cashierNameField = new TextField();
        this.cashierNumberLabel = new Label("Id number:");
        this.cashierNumberField = new TextField();
        this.cashierSalaryLabel = new Label("Salary:");
        this.cashierSalaryField = new TextField();
        this.submitBtn = new Button();
        this.submitBtn.setText("Add");
        this.submitBtn.setPrefWidth(100);
        this.submitBtn.setOnAction(this::addSubmitBtnHandle);
        this.refreshList();
    }

    public Node setupCashiersWindow() {
        GridPane pane = WindowUtil.createGridPane(6, 5);

        pane.add(this.listView, 0, 0, 2, 10);
        pane.add(this.cashierNumberLabel, 2, 0);
        pane.add(this.cashierNumberField, 3, 0);
        pane.add(this.cashierNameLabel, 2, 1);
        pane.add(this.cashierNameField, 3, 1);
        pane.add(this.cashierSalaryLabel, 2, 2);
        pane.add(this.cashierSalaryField, 3, 2);
        pane.add(this.submitBtn, 2, 3);

        return pane;
    }

    private void refreshList() {
        items.clear();
        shop.getCashiers().forEach(c -> items.add(c.toString()));
    }

    private void addSubmitBtnHandle(ActionEvent event) {
        if (cashierNameField.getText().trim().isEmpty() || !cashierNameField.getText().trim().matches(NAME_REGEX) ||
                cashierNumberField.getText().trim().isEmpty() || !cashierNumberField.getText().trim().matches(NUMBER_REGEX) ||
                cashierSalaryField.getText().trim().isEmpty() || !cashierSalaryField.getText().trim().matches(DECIMAL_REGEX)) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }
        Cashier cashier = new Cashier(
                cashierNameField.getText().trim(),
                Integer.parseInt(cashierNumberField.getText().trim()),
                Double.parseDouble(cashierSalaryField.getText().trim())
        );
        try {
            shop.addCashier(cashier);
            refreshList();
        } catch (IllegalArgumentException | NullPointerException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private ListCell<String> addListViewContextMenu(ListView<String> stringListView) {
        ListCell<String> cell = new ListCell<>();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete %s", cell.itemProperty()));
        deleteItem.setOnAction(event -> {
            try {
                Pattern p = Pattern.compile(ID_REGEX);
                Matcher m = p.matcher(cell.itemProperty().toString());
                if (!m.find()) return;
                Cashier cashier = new Cashier(Integer.parseInt(m.group(1)));
                shop.removeCashier(cashier);
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
