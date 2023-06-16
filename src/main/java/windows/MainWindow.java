package windows;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static constants.EnvironmentConstants.*;

public class MainWindow extends Application {

    public void start(Stage primaryStage) {
        CashiersWindow cashiersWindow = new CashiersWindow();
        CashDesksWindow cashDesksWindow = new CashDesksWindow();
        ProductsWindow productsWindow = new ProductsWindow();
        CartWindow cartWindow = new CartWindow();
        ReceiptsWindow receiptsWindow = new ReceiptsWindow();
        DeliveryWindow deliveryWindow = new DeliveryWindow();
        StatsWindow statsWindow = new StatsWindow();

        TabPane tabPane = new TabPane();
        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox, WINDOW_WIDTH, WINDOW_HEIGHT);

        Tab cashiersTab = new Tab(CASHIERS_TAB_NAME, cashiersWindow.setupCashiersWindow());
        cashiersTab.setClosable(false);

        Tab cashDesksTab = new Tab(CASH_DESKS_TAB_NAME, cashDesksWindow.setupCashDesksWindow());
        cashDesksTab.setClosable(false);

        Tab productsTab = new Tab(PRODUCTS_TAB_NAME, productsWindow.setupProductsWindow());
        productsTab.setClosable(false);

        Tab cartTab = new Tab(CART_TAB_NAME, cartWindow.setupCartWindow());
        cartTab.setClosable(false);

        Tab receiptsTab = new Tab(RECEIPTS_TAB_NAME, receiptsWindow.setupReceiptsWindow());
        receiptsTab.setClosable(false);

        Tab deliveryTab = new Tab(DELIVERY_TAB_NAME, deliveryWindow.setupDeliveryWindow());
        deliveryTab.setClosable(false);

        Tab statsTab = new Tab(STATS_TAB_NAME, statsWindow.setupStatsWindow());
        statsTab.setClosable(false);

        tabPane.getTabs()
                .addAll(cashiersTab, cashDesksTab, productsTab, cartTab, receiptsTab, deliveryTab, statsTab);

        primaryStage.setScene(scene);
        primaryStage.setTitle(APP_NAME);
        primaryStage.show();
    }

    public void launchApp(String[] args) {
        launch(args);
    }

}
