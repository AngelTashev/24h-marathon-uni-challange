
package windows;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.basic.*;
import models.enums.ProductCategory;
import models.transaction.store.StoreItem;
import util.AlertUtil;
import util.GeneralUtil;
import util.WindowUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static constants.StringsConstants.*;

public class ProductsWindow {
    private final Shop shop;
    private final ListView<String> listView;
    private final ObservableList<String> items;

    private final Label productNumberLabel;
    private final TextField productNumberField;
    private final Label productNameLabel;
    private final TextField productNameField;
    private final Label productCategoryLabel;
    private final ComboBox<String> productCategoryComboBox;
    private final Label productDeliveryPriceLabel;
    private final TextField productDeliveryPriceField;
    private final Label productSalePriceLabel;
    private final TextField productSalePriceField;
    private final Label productMarkupLabel;
    private final TextField productMarkupField;
    private final Label productExpiryDateLabel;
    private final TextField productExpiryDateField;
    private final Label productExpiryMarkupLabel;
    private final TextField productExpiryMarkupField;

    private final Button submitBtn;

    private ProductCategory category;

    private final DateFormat dateFormatter;

    public ProductsWindow() {
        this.items = FXCollections.observableArrayList();
        this.listView = WindowUtil.createListView(this.items);
        this.listView.setCellFactory(this::addListViewContextMenu);
        this.shop = Shop.getInstance();

        this.dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);

        this.productNumberLabel = new Label("Id number:");
        this.productNumberField = new TextField();
        this.productNameLabel = new Label("Name:");
        this.productNameField = new TextField();
        this.productSalePriceLabel = new Label("Sale:");
        this.productSalePriceField = new TextField();
        this.productDeliveryPriceLabel = new Label("Delivery:");
        this.productDeliveryPriceField = new TextField();
        this.productMarkupLabel = new Label("Markup %:");
        this.productMarkupField = new TextField();
        this.productExpiryMarkupLabel = new Label("Markup days:");
        this.productExpiryMarkupField = new TextField();
        this.productExpiryDateLabel = new Label("Expiry:");
        this.productExpiryDateField = new TextField();
        this.productCategoryLabel = new Label("Category:");
        this.productCategoryComboBox = new ComboBox<>();
        this.productCategoryComboBox.setPromptText("Select category");
        this.productCategoryComboBox.getSelectionModel().selectFirst();
        this.productCategoryComboBox.setEditable(true);
        this.productCategoryComboBox.setItems(FXCollections.observableArrayList(
                ProductCategory.PRODUCT_NONFOOD.toString(),
                ProductCategory.PRODUCT_FOOD.toString()
        ));
        this.productCategoryComboBox.valueProperty().addListener(this::addCategoryComboBoxChangeListener);

        this.submitBtn = new Button();
        this.submitBtn.setText("Add");
        this.submitBtn.setPrefWidth(100);
        this.submitBtn.setOnAction(this::addSubmitBtnHandle);
        this.refreshList();
    }

    public Node setupProductsWindow() {
        GridPane pane = WindowUtil.createGridPane(10, 5);

        pane.add(this.listView, 0, 0, 2, 10);
        pane.add(this.productNumberLabel, 2, 0);
        pane.add(this.productNumberField, 3, 0);
        pane.add(this.productNameLabel, 2, 1);
        pane.add(this.productNameField, 3, 1);
        pane.add(this.productCategoryLabel, 2, 2);
        pane.add(this.productCategoryComboBox, 3, 2);
        pane.add(this.productSalePriceLabel, 2, 3);
        pane.add(this.productSalePriceField, 3, 3);
        pane.add(this.productDeliveryPriceLabel, 2, 4);
        pane.add(this.productDeliveryPriceField, 3, 4);
        pane.add(this.productMarkupLabel, 2, 5);
        pane.add(this.productMarkupField, 3, 5);
        pane.add(this.productExpiryMarkupLabel, 2, 6);
        pane.add(this.productExpiryMarkupField, 3, 6);
        pane.add(this.productExpiryDateLabel, 2, 7);
        pane.add(this.productExpiryDateField, 3, 7);
        pane.add(this.submitBtn, 2, 8);

        return pane;
    }

    private void refreshList() {
        items.clear();
        shop.getItemsInStore().forEach(c -> items.add(c.toString()));
    }

    private void addSubmitBtnHandle(ActionEvent event) {
        if (productNumberField.getText().trim().isEmpty() || !productNumberField.getText().trim().matches(NUMBER_REGEX) ||
            productNameField.getText().trim().isEmpty() || !productNameField.getText().trim().matches(NAME_REGEX) ||
            productSalePriceField.getText().trim().isEmpty() || !productSalePriceField.getText().trim().matches(DECIMAL_REGEX) ||
            productDeliveryPriceField.getText().trim().isEmpty() || !productDeliveryPriceField.getText().trim().matches(DECIMAL_REGEX) ||
            productMarkupField.getText().trim().isEmpty() || !productMarkupField.getText().trim().matches(DECIMAL_REGEX) ||
            productExpiryMarkupField.getText().trim().isEmpty() || !productExpiryMarkupField.getText().trim().matches(NUMBER_REGEX) ||
            productExpiryDateField.getText().trim().isEmpty() || !GeneralUtil.validateDate(productExpiryDateField.getText().trim())
        ) {
            AlertUtil.createAlert(INVALID_FORM_ALERT_TITLE, INVALID_FORM_ALERT_CONTENT, Alert.AlertType.WARNING);
            return;
        }

        try {
            Product product = new Product(
                    productNameField.getText().trim(),
                    Integer.parseInt(productNumberField.getText().trim()),
                    this.category,
                    Double.parseDouble(productDeliveryPriceField.getText().trim()),
                    Double.parseDouble(productSalePriceField.getText().trim()),
                    Double.parseDouble(productMarkupField.getText().trim()),
                    dateFormatter.parse(productExpiryDateField.getText().trim()),
                    Integer.parseInt(productExpiryMarkupField.getText().trim())
            );
            StoreItem storeItem = new StoreItem(product, 0);
            shop.addItemToStore(storeItem);
            refreshList();
        } catch (IllegalArgumentException | NullPointerException | ParseException ex) {
            AlertUtil.createAlert(INVALID_DATA_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void addCategoryComboBoxChangeListener(ObservableValue<? extends String> observableValue, String s, String s1) {
        if (s != null) {
            this.category = ProductCategory.valueOf(s);
        }
    }

    private ListCell<String> addListViewContextMenu(ListView<String> stringListView) {
        ListCell<String> cell = new ListCell<>();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem();
        MenuItem refreshItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete %s", cell.itemProperty()));
        refreshItem.textProperty().bind(Bindings.format("refresh", cell.itemProperty()));
        deleteItem.setOnAction(event -> {
            try {
                int id = GeneralUtil.returnId(cell.itemProperty().toString());
                if (id < 0) return;
                Product product = new Product(id);
                StoreItem storeItem = new StoreItem(product, 0);
                shop.removeItemFromStore(storeItem);
                refreshList();
            } catch (IndexOutOfBoundsException ex) {
                AlertUtil.createAlert(ERROR_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
        refreshItem.setOnAction(event -> {
            this.refreshList();
        });
        contextMenu.getItems().add(refreshItem);
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

