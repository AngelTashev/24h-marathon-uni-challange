package models.basic;

import java.util.Objects;

public class Cashier {
    private String name;
    private int identityNumber;
    private double monthlySalary;

    public Cashier() {}

    public Cashier(int identityNumber) {
        this.setIdentityNumber(identityNumber);
    }

    public Cashier(String name, int identityNumber, double monthlySalary) {
        this.setName(name);
        this.setIdentityNumber(identityNumber);
        this.setMonthlySalary(monthlySalary);
    }

    public String getName() {
        return name;
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

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = Math.max(identityNumber, 0);
    }

    @Override
    public String toString() {
        return String.format("#%d#\n%s\n%.2f lv./mo", this.identityNumber, this.name, this.monthlySalary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cashier cashier = (Cashier) o;
        return identityNumber == cashier.identityNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityNumber);
    }
}
