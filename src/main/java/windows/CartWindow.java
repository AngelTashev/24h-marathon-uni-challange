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
import models.basic.Product;
import models.basic.Shop;
import models.transaction.sale.Sale;
import models.transaction.sale.SaleItem;
import models.transaction.store.StoreItem;
import util.AlertUtil;
import util.GeneralUtil;
import util.WindowUtil;

import java.util.Date;

import static constants.StringsConstants.*;

public class CartWindow {
    private final Shop shop;
    private final ListView<String> listView;
    private final ObservableList<String> items;

    private final Label cartBalanceLabel;
    private final TextField cartBalanceField;
    private final Label cartCashDeskLabel;
    private final ComboBox<String> cartCashDeskComboBoxField;

    private final Label cartProductComboBoxLabel;
    private final ComboBox<String> cartProductComboBoxField;


    private final Label totalPriceLabel;

    private final Button submitBtn;
    private final Button addProductToSaleBtn;

    private CashDesk currCashDesk;
    private StoreItem currStoreItem;
    private Sale sale;


    public CartWindow() {
        this.sale = new Sale();
        this.items = FXCollections.observableArrayList();
        this.listView = WindowUtil.createListView(this.items);
        this.listView.setCellFactory(this::addListViewContextMenu);
        this.shop = Shop.getInstance();

        this.cartBalanceLabel = new Label("Balance:");
        this.cartBalanceField = new TextField();

        this.cartCashDeskLabel = new Label("Cash desk:");
        this.cartCashDeskComboBoxField = new ComboBox<>();
        this.cartCashDeskComboBoxField.setPromptText("Cash desk");
        this.cartCashDeskComboBoxField.setEditable(true);
        this.cartCashDeskComboBoxField.focusedProperty().addListener((observable, oldValue, newValue) -> refreshCashDeskComboBox());
        this.cartCashDeskComboBoxField.valueProperty().addListener(this::addCashDeskComboBoxChangeListener);
        this.refreshCashDeskComboBox();

        this.cartProductComboBoxLabel = new Label("Product:");
        this.cartProductComboBoxField = new ComboBox<>();
        this.cartProductComboBoxField.setPromptText("Product");
        this.cartProductComboBoxField.setEditable(true);
        this.cartProductComboBoxField.focusedProperty().addListener((observable, oldValue, newValue) -> refreshStoreItemsList());
        this.cartProductComboBoxField.valueProperty().addListener(this::addStoreItemComboBoxChangeListener);
        this.refreshStoreItemsList();

        this.submitBtn = new Button();
        this.submitBtn.setText("Finish");
        this.submitBtn.setPrefWidth(100);
        this.submitBtn.setOnAction(this::addSubmitBtnHandle);

        this.addProductToSaleBtn = new Button();
        this.addProductToSaleBtn.setText("Add");
        this.addProductToSaleBtn.setPrefWidth(50);
        this.addProductToSaleBtn.setOnAction(this::addProductToCartHandle);

        this.totalPriceLabel = new Label("Total: 0 lv.");
        this.refreshList();
    }

    public Node setupCartWindow() {
        GridPane pane = WindowUtil.createGridPane(10, 5);

        pane.add(this.listView, 0, 0, 2, 10);
        pane.add(this.cartBalanceLabel, 2, 0);
        pane.add(this.cartBalanceField, 3, 0);
        pane.add(this.cartCashDeskLabel, 2, 1);
        pane.add(this.cartCashDeskComboBoxField, 3, 1);
        pane.add(this.cartProductComboBoxLabel, 2, 2);
        pane.add(this.cartProductComboBoxField, 3, 2);
        pane.add(this.addProductToSaleBtn, 4, 2);
        pane.add(this.submitBtn, 2, 3);
        pane.add(this.totalPriceLabel, 2, 4);

        return pane;
    }

    private void refreshList() {
        items.clear();
        sale.getItems().forEach(c -> items.add(c.toString()));
    }

    private void refreshStoreItemsList() {
        cartProductComboBoxField.setItems(
                FXCollections.observableArrayList(
                        shop.getItemsInStore().stream().map(StoreItem::toString).toList()
                ));
    }

    private void refreshCashDeskComboBox() {
        cartCashDeskComboBoxField.setItems(
                FXCollections.observableArrayList(
                        shop.getCashDesks().stream().map(CashDesk::toString).toList()
                ));
    }

    private void updateTotalPrice() {
        this.totalPriceLabel.setText(String.format("Total: %.2f lv.", this.sale.getTotalPrice()));
    }

    private void addProductToCartHandle(ActionEvent event) {
        // TODO -> don't sell if expiry date has passed
        if (this.currStoreItem == null) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }
        SaleItem saleItem = new SaleItem(
                this.currStoreItem.getProduct(),
                1
        );
        StoreItem checkItem = new StoreItem(this.currStoreItem.getProduct(), 0);
        int index = sale.getItemIndex(saleItem);
        Date today = new Date();
        try {
            if (index < 0) {
                if (shop.getStoreItem(checkItem).getQuantity() == 0 ||
                        shop.getStoreItem(checkItem).getProduct().getExpiryDate().getTime() <= today.getTime()) {

                    AlertUtil.createAlert(QUANTITY_SHORTAGE_ALERT_TITLE,
                            String.format(QUANTITY_SHORTAGE_ALERT_CONTENT, saleItem.getProduct().getName()), Alert.AlertType.INFORMATION);
                    return;
                }
                this.sale.addItem(saleItem);
            } else {
                SaleItem updateItem = (SaleItem) sale.getItem(index);
                if (updateItem.getQuantity() >= shop.getStoreItem(checkItem).getQuantity() ||
                        shop.getStoreItem(checkItem).getProduct().getExpiryDate().getTime() <= today.getTime()) {
                    AlertUtil.createAlert(QUANTITY_SHORTAGE_ALERT_TITLE,
                            String.format(QUANTITY_SHORTAGE_ALERT_CONTENT, updateItem.getProduct().getName()), Alert.AlertType.INFORMATION);
                    return;
                }
                updateItem.setQuantity(updateItem.getQuantity() + 1);
            }
            updateTotalPrice();
            refreshList();
        } catch (IllegalArgumentException | NullPointerException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addSubmitBtnHandle(ActionEvent event) {
        if (this.cartBalanceField.getText().isEmpty() || !this.cartBalanceField.getText().matches(DECIMAL_REGEX) ||
            this.currCashDesk == null || this.sale.getItems().size() == 0) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }
        double balance = Double.parseDouble(this.cartBalanceField.getText().trim());
        double totalPrice = this.sale.getTotalPrice();
        if ((balance - totalPrice) < 0) {
            AlertUtil.createAlert(BALANCE_SHORT_TITLE,
                    String.format(BALANCE_SHORT_CONTENT, Math.abs(balance - totalPrice)), Alert.AlertType.INFORMATION);
            return;
        }
        this.shop.addSale(this.sale);
        this.shop.updateStoreQuantities(this.sale);
        this.shop.generateReceipt(this.sale, this.currCashDesk);
        this.sale = new Sale();
        this.refreshList();
        this.refreshStoreItemsList();
        this.updateTotalPrice();
        try {
            refreshList();
        } catch (IllegalArgumentException | NullPointerException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addCashDeskComboBoxChangeListener(ObservableValue<? extends String> observableValue, String s, String s1) {
        int id = GeneralUtil.returnId(s);
        if (id < 0) return;
        this.currCashDesk = this.shop.getCashDesk(new CashDesk(id));
    }

    private void addStoreItemComboBoxChangeListener(ObservableValue<? extends String> observableValue, String s, String s1) {
        int id = GeneralUtil.returnId(s);
        if (id < 0) return;
        Product product = new Product(id);
        this.currStoreItem = this.shop.getStoreItem(new StoreItem(product, 0));
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
                Product product = new Product(id);
                SaleItem saleItem = new SaleItem(product, 0);
                sale.removeItem(saleItem);
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
