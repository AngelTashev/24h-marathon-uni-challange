package models.transaction.delivery;

import models.basic.Product;
import models.transaction.base.BaseTransactionItem;

public class DeliveryItem extends BaseTransactionItem {

    public DeliveryItem(Product product, int quantity) {
        super(product, quantity);
    }

    @Override
    public double getTotalPrice() {
       return this.getProduct().getDeliveryPrice() * this.getQuantity();
    }


    @Override
    public String toString() {
        return String.format("%s\nTo order: %d", getProduct(), getQuantity());
    }
}
