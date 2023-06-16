package windows;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.basic.Receipt;
import models.basic.Shop;
import util.AlertUtil;
import util.WindowUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.StringsConstants.*;

public class ReceiptsWindow {
    private final Shop shop;
    private final ListView<String> listView;
    private final ObservableList<String> items;

    private final Label receiptViewLabel;


    public ReceiptsWindow() {
        this.items = FXCollections.observableArrayList();
        this.listView = WindowUtil.createListView(this.items);
        this.listView.setCellFactory(this::addListViewContextMenu);
        this.shop = Shop.getInstance();

        this.receiptViewLabel = new Label("Select a receipt to view it");
        this.refreshList();
    }

    public Node setupReceiptsWindow() {
        GridPane pane = WindowUtil.createGridPane(6, 5);

        pane.add(this.listView, 0, 0, 2, 10);
        pane.add(this.receiptViewLabel, 2, 0, 4, 10);

        return pane;
    }

    private void refreshList() {
        items.clear();
        items.add("refresh");
        shop.getReceipts().forEach(c -> items.add(c.getReceiptIdString()));
    }

    private ListCell<String> addListViewContextMenu(ListView<String> stringListView) {
        ListCell<String> cell = new ListCell<>();
        ContextMenu contextMenu = new ContextMenu();

        MenuItem viewItem = new MenuItem();
        viewItem.textProperty().bind(Bindings.format("View %s", cell.itemProperty()));
        viewItem.setOnAction(event -> {
            if (cell.itemProperty().getValue().equals("refresh")) {
                this.refreshList();
                return;
            }
            try {
                Pattern p = Pattern.compile(ID_REGEX);
                Matcher m = p.matcher(cell.itemProperty().toString());
                if (!m.find()) return;
                Receipt receipt = new Receipt(Integer.parseInt(m.group(1)));
                this.receiptViewLabel.setText(shop.getReceipt(receipt).toString());
                refreshList();
            } catch (IndexOutOfBoundsException ex) {
                AlertUtil.createAlert(ERROR_ALERT_TITLE, ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
        contextMenu.getItems().add(viewItem);

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
