package models.transaction.sale;

import models.transaction.base.BaseTransaction;
import models.transaction.base.ITransactionItem;

import static constants.StringsConstants.*;

public class Sale extends BaseTransaction {

    @Override
    public boolean addItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        if (item == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, SaleItem.class.getSimpleName()));
        }
        if (!(item instanceof SaleItem)) {
            throw new IllegalArgumentException(
                    String.format(ITEM_MUST_BE_INSTANCE_ADDING,
                            SaleItem.class.getSimpleName(),
                            Sale.class.getSimpleName())
            );
        }
        return super.addItem(item);
    }

    @Override
    public boolean removeItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        if (item == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, SaleItem.class.getSimpleName()));
        }
        if (!(item instanceof SaleItem)) {
            throw new IllegalArgumentException(
                    String.format(ITEM_MUST_BE_INSTANCE_REMOVING,
                            SaleItem.class.getSimpleName(),
                            Sale.class.getSimpleName())
            );
        }
        return super.removeItem(item);
    }
}
