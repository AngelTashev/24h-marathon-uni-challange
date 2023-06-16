package models.basic;

import models.enums.ProductCategory;

import java.util.Date;

import static constants.EnvironmentConstants.MILLISECONDS_IN_A_DAY;

public class Product {
    private String name;
    private int identityNumber;
    private ProductCategory category;
    private double deliveryPrice;
    private double salePrice;
    private double markupPercent;
    private Date expiryDate;
    private int expiryMarkupDays;

    public Product() {}

    public Product(int identityNumber) {
        this.setIdentityNumber(identityNumber);
    }

    public Product(String name, int identityNumber, ProductCategory category, double deliveryPrice, double salePrice, double markupPercent, Date expiryDate, int expiryMarkupDays) {
        this.setName(name);
        this.setIdentityNumber(identityNumber);
        this.setCategory(category);
        this.setDeliveryPrice(deliveryPrice);
        this.setSalePrice(salePrice);
        this.setMarkupPercent(markupPercent);
        this.setExpiryDate(expiryDate);
        this.setExpiryMarkupDays(expiryMarkupDays);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name == null ? "no name" : name;
    }

    public int getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(int identityNumber) {
        this.identityNumber = Math.max(identityNumber, 0);
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category == null ? ProductCategory.PRODUCT_NONFOOD : category;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = Math.max(deliveryPrice, 0);;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = Math.max(salePrice, 0);;
    }

    public double getMarkupPercent() {
        return markupPercent;
    }

    public void setMarkupPercent(double markupPercent) {
        this.markupPercent = Math.max(markupPercent, 0);;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate == null ? new Date() : expiryDate;
    }

    public int getExpiryMarkupDays() {
        return expiryMarkupDays;
    }

    public void setExpiryMarkupDays(int expiryMarkupDays) {
        this.expiryMarkupDays = Math.max(expiryMarkupDays, 0);
    }

    public String toStoreString() {
        return String.format("#%d#\n%s\n%s\nSale: %.2f lv.\nExpiry: %s",
                this.identityNumber, this.name,
                this.category, this.calcStorePrice(),
                this.expiryDate == null ? "Somewhere in the future..." : this.expiryDate.toString());
    }

    public double calcStorePrice() {
        double result = this.salePrice;
        if (this.expiryDate == null) {
            return result;
        }

        Date today = new Date();
        if (((this.expiryDate.getTime() - today.getTime()) / MILLISECONDS_IN_A_DAY) <= this.expiryMarkupDays) {
            result = result * (1 - (this.markupPercent / 100));
        }
        return result;
    }

    @Override
    public String toString() {
        return String.format("#%d#\n%s\n%s\nSale/Delivery: %.2f/%.2f lv.\nMarkup/days: %.2f%%/%d\nExpiry: %s",
                this.identityNumber, this.name,
                this.category, this.salePrice, this.deliveryPrice,
                this.markupPercent, this.expiryMarkupDays,
                this.expiryDate == null ? "Somewhere in the future..." : this.expiryDate.toString());
    }
}
