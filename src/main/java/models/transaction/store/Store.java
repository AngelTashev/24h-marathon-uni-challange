package models.transaction.store;

import models.transaction.base.BaseTransaction;
import models.transaction.base.ITransactionItem;

import static constants.StringsConstants.*;

public class Store extends BaseTransaction {
    @Override
    public boolean addItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        if (item == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, StoreItem.class.getSimpleName()));
        }
        if (!(item instanceof StoreItem)) {
            throw new IllegalArgumentException(
                    String.format(ITEM_MUST_BE_INSTANCE_ADDING,
                            StoreItem.class.getSimpleName(),
                            Store.class.getSimpleName())
            );
        }
        return super.addItem(item);
    }

    @Override
    public boolean removeItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        if (item == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, StoreItem.class.getSimpleName()));
        }
        if (!(item instanceof StoreItem)) {
            throw new IllegalArgumentException(
                    String.format(ITEM_MUST_BE_INSTANCE_REMOVING,
                            StoreItem.class.getSimpleName(),
                            Store.class.getSimpleName())
            );
        }
        return super.removeItem(item);
    }
}
