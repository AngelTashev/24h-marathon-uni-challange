package models.transaction.base;

import java.util.List;

public interface ITransaction {
    double getTotalPrice();

    boolean addItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException;

    boolean removeItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException;

    int getItemIndex(ITransactionItem item);

    ITransactionItem getItem(int index);

    List<ITransactionItem> getItems();

}
