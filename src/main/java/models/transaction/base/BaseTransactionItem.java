package models.transaction.base;

import models.basic.Product;

import java.util.Objects;

public abstract class BaseTransactionItem implements ITransactionItem {

    private Product product;
    private int quantity;

    // TODO: Date?

    public BaseTransactionItem() {}

    public BaseTransactionItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public double getTotalPrice() {
        return product.calcStorePrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTransactionItem that = (BaseTransactionItem) o;
        return Objects.equals(product.getIdentityNumber(), that.product.getIdentityNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getIdentityNumber());
    }
}
