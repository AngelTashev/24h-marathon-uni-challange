package models.transaction.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseTransaction implements ITransaction {
    protected List<ITransactionItem> items;

    public BaseTransaction() {
        this.items = new ArrayList<>();
    }

    @Override
    public double getTotalPrice() {
        return items.stream()
                .map(ITransactionItem::getTotalPrice)
                .reduce(0.0, Double::sum);
    }

    @Override
    public boolean addItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        return this.items.add(item);
    }

    @Override
    public boolean removeItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        return this.items.remove(item);
    }

    @Override
    public int getItemIndex(ITransactionItem item) {
        return this.items.indexOf(item);
    }

    @Override
    public ITransactionItem getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public List<ITransactionItem> getItems() {
        return Collections.unmodifiableList(this.items);
    }
}
