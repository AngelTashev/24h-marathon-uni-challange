package models.basic;

import models.transaction.sale.Sale;

import java.util.Date;
import java.util.Objects;

import static constants.StringsConstants.SEPARATOR;

public class Receipt {

    private int receiptNumber;
    private CashDesk cashDesk;
    private Date date;
    private Sale sale;

    public Receipt() {
        this.date = new Date();
    }

    public Receipt(int receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Receipt(int receiptNumber, CashDesk cashDesk, Date date, Sale sale) {
        this.receiptNumber = receiptNumber;
        this.cashDesk = cashDesk;
        this.date = date == null ? new Date() : date;
        this.sale = sale;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(int receiptNumber) {
        this.receiptNumber = Math.max(receiptNumber, 0);
    }

    public CashDesk getCashDesk() {
        return cashDesk;
    }

    public void setCashDesk(CashDesk cashDesk) {
        this.cashDesk = cashDesk == null ? new CashDesk() : cashDesk;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date == null ? new Date() : date;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale == null ? new Sale() : sale;
    }

    public String getReceiptIdString() {
        return String.format("#%d#", this.receiptNumber);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Receipt #").append(this.receiptNumber);
        sb.append("\nDate and time: ").append(this.date.toString());
        sb.append("\n").append(SEPARATOR);
        sb.append("\nCash desk #").append(this.cashDesk.getDeskNumber());
        sb.append("\nCashier #").append(this.cashDesk.getCashier().getIdentityNumber())
                .append(", ").append(this.cashDesk.getCashier().getName());
        sb.append("\n").append(SEPARATOR);
        sb.append("\nProducts\n").append(SEPARATOR);
        this.sale.getItems().forEach(item -> {
            sb.append("\n\t").append(item.getProduct().toStoreString());
            sb.append("\nBought: ").append(item.getQuantity());
        });
        sb.append("\n").append(SEPARATOR);
        sb.append("\nTotal: ").append(this.sale.getTotalPrice());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return receiptNumber == receipt.receiptNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptNumber);
    }
}
