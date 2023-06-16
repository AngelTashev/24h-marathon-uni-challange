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
import models.transaction.delivery.Delivery;
import models.transaction.delivery.DeliveryItem;
import models.transaction.sale.SaleItem;
import models.transaction.store.StoreItem;
import util.AlertUtil;
import util.GeneralUtil;
import util.WindowUtil;

import static constants.StringsConstants.*;

public class DeliveryWindow {
    private final Shop shop;
    private final ListView<String> listView;
    private final ObservableList<String> items;

    private final Label deliveryProductComboBoxLabel;
    private final ComboBox<String> deliveryProductComboBoxField;

    private final Label totalPriceLabel;

    private final Button submitBtn;
    private final Button addProductToSaleBtn;

    private CashDesk currCashDesk;
    private StoreItem currStoreItem;
    private Delivery delivery;


    public DeliveryWindow() {
        this.delivery = new Delivery();
        this.items = FXCollections.observableArrayList();
        this.listView = WindowUtil.createListView(this.items);
        this.listView.setCellFactory(this::addListViewContextMenu);
        this.shop = Shop.getInstance();

        this.deliveryProductComboBoxLabel = new Label("Product:");
        this.deliveryProductComboBoxField = new ComboBox<>();
        this.deliveryProductComboBoxField.setPromptText("Product");
        this.deliveryProductComboBoxField.setEditable(true);
        this.deliveryProductComboBoxField.focusedProperty().addListener((observable, oldValue, newValue) -> refreshStoreItemsList());
        this.deliveryProductComboBoxField.valueProperty().addListener(this::addStoreItemComboBoxChangeListener);
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

    public Node setupDeliveryWindow() {
        GridPane pane = WindowUtil.createGridPane(10, 5);

        pane.add(this.listView, 0, 0, 2, 10);
        pane.add(this.deliveryProductComboBoxLabel, 2, 1);
        pane.add(this.deliveryProductComboBoxField, 3, 1);
        pane.add(this.addProductToSaleBtn, 4, 1);
        pane.add(this.submitBtn, 2, 2);
        pane.add(this.totalPriceLabel, 2, 3);

        return pane;
    }

    private void refreshList() {
        items.clear();
        delivery.getItems().forEach(c -> items.add(c.toString()));
    }

    private void refreshStoreItemsList() {
        deliveryProductComboBoxField.setItems(
                FXCollections.observableArrayList(
                        shop.getItemsInStore().stream().map(StoreItem::toString).toList()
                ));
    }

    private void updateTotalPrice() {
        this.totalPriceLabel.setText(String.format("Total: %.2f lv.", this.delivery.getTotalPrice()));
    }

    private void addProductToCartHandle(ActionEvent event) {
        if (this.currStoreItem == null) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }
        DeliveryItem deliveryItem = new DeliveryItem(
                this.currStoreItem.getProduct(),
                1
        );
        int index = delivery.getItemIndex(deliveryItem);
        try {
            if (index < 0) {
                this.delivery.addItem(deliveryItem);
            } else {
                DeliveryItem updateItem = (DeliveryItem) delivery.getItem(index);
                updateItem.setQuantity(updateItem.getQuantity() + 1);
            }
            updateTotalPrice();
            refreshList();
        } catch (IllegalArgumentException | NullPointerException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addSubmitBtnHandle(ActionEvent event) {
        if (this.delivery.getItems().size() == 0) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }
        this.shop.addDelivery(this.delivery);
        this.shop.updateStoreQuantities(this.delivery);
        this.delivery = new Delivery();
        this.refreshList();
        this.refreshStoreItemsList();
        this.updateTotalPrice();
        try {
            refreshList();
        } catch (IllegalArgumentException | NullPointerException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
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
                delivery.removeItem(saleItem);
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
