package windows;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import models.basic.Receipt;
import models.basic.Shop;
import util.WindowUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constants.StringsConstants.*;

public class StatsWindow {
    private final Shop shop;

    private final Label titleLabel;
    private final Label content;
    private final Button refreshButton;


    public StatsWindow() {
        this.shop = Shop.getInstance();

        this.titleLabel = new Label(String.format("SOOooo, drawing the line...\n%s\n", SEPARATOR));
        this.content = new Label("Loading...");
        this.refreshButton = new Button();
        this.refreshButton.setText("Refresh");
        this.refreshButton.setPrefWidth(60);
        this.refreshButton.setOnAction(this::addRefreshBtnHandle);
        this.updateInfo();
    }

    public Node setupStatsWindow() {
        GridPane pane = WindowUtil.createGridPane(6, 5);

        pane.add(this.titleLabel, 1, 0, 4, 2);
        pane.add(this.refreshButton, 3, 0);
        pane.add(this.content, 1, 1, 5, 8);

        return pane;
    }

    private void addRefreshBtnHandle(ActionEvent event) {
        this.updateInfo();
    }

    private void updateInfo() {
        this.content.setText(this.shop.toString());
    }

}
