package models.transaction.base;

import models.basic.Product;

public interface ITransactionItem {
    double getTotalPrice();

    int getQuantity();
    void setQuantity(int quantity);

    Product getProduct();

    void setProduct(Product product);
}
