package models.transaction.store;

import models.basic.Product;
import models.transaction.base.BaseTransactionItem;

public class StoreItem extends BaseTransactionItem {

    public StoreItem(Product product, int quantity) {
        super(product, quantity);
    }

    @Override
    public String toString() {
        return String.format("%s\nIn stock: %d", getProduct(), getQuantity());
    }
}
