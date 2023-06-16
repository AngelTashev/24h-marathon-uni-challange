package models.transaction.sale;

import models.basic.Product;
import models.transaction.base.BaseTransactionItem;

public class SaleItem extends BaseTransactionItem {

    public SaleItem(Product product, int quantity) {
        super(product, quantity);
    }

    @Override
    public String toString() {
        return String.format("%s\nTo buy: %d", getProduct().toStoreString(), getQuantity());
    }
}
