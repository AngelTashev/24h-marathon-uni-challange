import models.basic.CashDesk;
import models.basic.Cashier;
import models.basic.Product;
import models.basic.Shop;
import models.enums.ProductCategory;
import models.transaction.store.StoreItem;
import windows.MainWindow;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Shop shop = Shop.getInstance();

        Cashier cashier = new Cashier("Penka", 43, 1500.5);
        shop.addCashier(cashier);

        CashDesk cashDesk = new CashDesk(112, cashier);
        shop.addCashDesk(cashDesk);

        Product product = new Product("apple", 556, ProductCategory.PRODUCT_FOOD, 1.1, 1.2, 30.5, new Date(), 5);
        StoreItem storeItem = new StoreItem(product, 5);
        shop.addItemToStore(storeItem);

        Product product2 = new Product("bleach", 1812, ProductCategory.PRODUCT_NONFOOD, 3, 5.4, 15.3, new Date(), 5);
        StoreItem storeItem2 = new StoreItem(product2, 10);
        shop.addItemToStore(storeItem2);

        MainWindow mainWindow = new MainWindow();
        mainWindow.launchApp(args);
    }
}
