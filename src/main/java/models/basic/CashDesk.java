package models.basic;

import java.util.Objects;

public class CashDesk {

    private int deskNumber;
    private Cashier cashier;

    public CashDesk() {}

    public CashDesk(int deskNumber) {
        this.setDeskNumber(deskNumber);
    }
    public CashDesk(int deskNumber, Cashier cashier) {
        this.setDeskNumber(deskNumber);
        this.setCashier(cashier);
    }

    public int getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(int deskNumber) {
        this.deskNumber = Math.max(deskNumber, 0);
    }

    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    @Override
    public String toString() {
        return String.format("#%d#\nCashier: #%d# %s",
                this.deskNumber,
                this.cashier == null ? -333 : this.cashier.getIdentityNumber(),
                this.cashier == null ? "A friendly ghost" : this.cashier.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashDesk cashDesk = (CashDesk) o;
        return deskNumber == cashDesk.deskNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(deskNumber);
    }
}
