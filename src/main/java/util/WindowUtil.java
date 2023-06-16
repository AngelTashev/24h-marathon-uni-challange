package util;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class WindowUtil {

    public static GridPane createGridPane(int numRows, int numCols) {
        GridPane pane = new GridPane();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            pane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            pane.getRowConstraints().add(rowConst);
        }
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        return pane;
    }

    public static ListView<String> createListView(ObservableList<String> items) {
        ListView<String> listView = new ListView<>();
        listView.setItems(items);
        listView.setPrefWidth(100);
        listView.setPrefHeight(300);
        return listView;
    }
}
