package models.basic;

import models.transaction.delivery.Delivery;
import models.transaction.sale.Sale;
import models.transaction.store.Store;
import models.transaction.store.StoreItem;
import util.FileUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static constants.StringsConstants.*;

public class Shop {
    private final List<CashDesk> cashDesks;
    private final List<Cashier> cashiers;
    private final List<Delivery> deliveries;
    private final List<Sale> sales;
    private final List<Receipt> receipts;
    private final Store productStore;

    private static Shop INSTANCE;

    public Shop() {
        this.cashDesks = new ArrayList<>();
        this.cashiers = new ArrayList<>();
        this.deliveries = new ArrayList<>();
        this.sales = new ArrayList<>();
        this.receipts = new ArrayList<>();
        this.productStore = new Store();
    }

    public static Shop getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Shop();
        }
        return INSTANCE;
    }

    public CashDesk getCashDesk(CashDesk cashDesk) {
        int index = this.cashDesks.indexOf(cashDesk);
        return index > -1 ? this.cashDesks.get(index) : null;
    }

    public boolean addCashDesk(CashDesk cashDesk) throws NullPointerException {
        if (cashDesk == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, CashDesk.class.getSimpleName()));
        }
        if (this.getCashDesk(cashDesk) != null) {
            throw new IllegalArgumentException(String.format(ITEM_ALREADY_EXISTS,
                    CashDesk.class.getSimpleName(), cashDesk.getDeskNumber()));
        }
        return this.cashDesks.add(cashDesk);
    }

    public boolean removeCashDesk(CashDesk cashDesk) throws NullPointerException {
        if (cashDesk == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, CashDesk.class.getSimpleName()));
        }
        return this.cashDesks.remove(cashDesk);
    }

    public List<CashDesk> getCashDesks() {
        return Collections.unmodifiableList(this.cashDesks);
    }

    public Cashier getCashier(Cashier cashier) {
        int index = this.cashiers.indexOf(cashier);
        return index > -1 ? this.cashiers.get(index) : null;
    }

    public void addCashier(Cashier cashier) throws IllegalArgumentException, NullPointerException {
        if (cashier == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Cashier.class.getSimpleName()));
        }
        if (this.getCashier(cashier) != null) {
            throw new IllegalArgumentException(String.format(ITEM_ALREADY_EXISTS,
                        Cashier.class.getSimpleName(), cashier.getIdentityNumber()));
        }
        this.cashiers.add(cashier);
    }

    public void removeCashier(Cashier cashier) throws NullPointerException {
        if (cashier == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Cashier.class.getSimpleName()));
        }
        this.cashiers.remove(cashier);
    }

    public List<Cashier> getCashiers() {
        return Collections.unmodifiableList(this.cashiers);
    }

    public void addDelivery(Delivery delivery) throws NullPointerException {
        if (delivery == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Delivery.class.getSimpleName()));
        }
        this.deliveries.add(delivery);
    }

    public void removeDelivery(Delivery delivery) throws NullPointerException {
        if (delivery == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Delivery.class.getSimpleName()));
        }
        this.deliveries.remove(delivery);
    }
    
    public List<Delivery> getDeliveries() {
        return Collections.unmodifiableList(this.deliveries);
    }
    
    public void addSale(Sale sale) throws NullPointerException {
        if (sale == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Sale.class.getSimpleName()));
        }
        this.sales.add(sale);
    }

    public void removeSale(Sale sale) throws NullPointerException {
        if (sale == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Sale.class.getSimpleName()));
        }
        this.sales.remove(sale);
    }
    
    public List<Sale> getSales() {
        return Collections.unmodifiableList(this.sales);
    }

    public Receipt getReceipt(Receipt receipt) {
        int index = this.receipts.indexOf(receipt);
        return index > -1 ? this.receipts.get(index) : null;
    }

    public void addReceipt(Receipt receipt) throws NullPointerException {
        if (receipt == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Receipt.class.getSimpleName()));
        }
        this.receipts.add(receipt);
    }

    public void removeReceipt(Receipt receipt) throws NullPointerException {
        if (receipt == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, Receipt.class.getSimpleName()));
        }
        this.receipts.remove(receipt);
    }

    public List<Receipt> getReceipts() {
        return Collections.unmodifiableList(this.receipts);
    }

    public StoreItem getStoreItem(StoreItem storeItem) {
        int index = this.productStore.getItemIndex(storeItem);
        return index > -1 ? (StoreItem) this.productStore.getItem(index) : null;
    }

    public void addItemToStore(StoreItem storeItem) throws NullPointerException {
        if (storeItem == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, StoreItem.class.getSimpleName()));
        }
        if (this.getStoreItem(storeItem) != null) {
            throw new IllegalArgumentException(String.format(ITEM_ALREADY_EXISTS,
                    Cashier.class.getSimpleName(), storeItem.getProduct().getIdentityNumber()));
        }
        this.productStore.addItem(storeItem);
    }

    public void removeItemFromStore(StoreItem storeItem) throws NullPointerException {
        if (storeItem == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, StoreItem.class.getSimpleName()));
        }
        this.productStore.removeItem(storeItem);
    }

    public List<StoreItem> getItemsInStore() {
        return this.productStore.getItems().stream().map(x -> (StoreItem)x).toList();
    }

    public void generateReceipt(Sale sale, CashDesk cashDesk) {
        Date today = new Date();
        int number = (this.receipts.size() + 1) * 129;
        String fileName = String.format("receipt-%d", number);
        Receipt receipt = new Receipt(number, cashDesk, today, sale);
        this.addReceipt(receipt);
        try {
            FileUtil.writeToFile(fileName, receipt.toString());
        } catch (IOException ex) {
            System.out.printf("Error writing to file %s: %s\n", fileName, ex.getMessage());
        }
    }

    public void updateStoreQuantities(Sale sale) {
        sale.getItems().forEach(item -> {
            StoreItem storeItem = new StoreItem(item.getProduct(), 0);
            int index = this.productStore.getItemIndex(storeItem);
            int quantity = this.productStore.getItem(index).getQuantity();
            this.productStore.getItem(index).setQuantity(quantity - item.getQuantity());
        });
    }

    public void updateStoreQuantities(Delivery delivery) {
        delivery.getItems().forEach(item -> {
            StoreItem storeItem = new StoreItem(item.getProduct(), 0);
            int index = this.productStore.getItemIndex(storeItem);
            int quantity = this.productStore.getItem(index).getQuantity();
            this.productStore.getItem(index).setQuantity(quantity + item.getQuantity());
        });
    }

    public double calcAllDeliveryCosts() {
        double sum = 0;
        for (Delivery delivery : this.deliveries)
            sum += delivery.getTotalPrice();
        return sum;
    }

    public double calcAllSalaryCosts() {
        double sum = 0;
        for (Cashier cashier : this.cashiers)
            sum += cashier.getMonthlySalary();
        return sum;
    }

    public double calcAllSalesIncome() {
        double sum = 0;
        for (Sale sale : this.sales)
            sum += sale.getTotalPrice();
        return sum;
    }

    @Override
    public String toString() {
        double deliverySpending = this.calcAllDeliveryCosts();
        double salarySpending = this.calcAllSalaryCosts();
        double salesIncome = this.calcAllSalesIncome();
        double totalProfit = salesIncome - (salarySpending + deliverySpending);
        return String.format(
                "Total spending on deliveries: -%.2f lv.\nTotal spending on salaries: -%.2f lv.\nTotal income from sales: +%.2f lv.\n%s\nTotal profit: %.2f",
                deliverySpending, salarySpending, salesIncome, SEPARATOR, totalProfit
        );
    }
}
