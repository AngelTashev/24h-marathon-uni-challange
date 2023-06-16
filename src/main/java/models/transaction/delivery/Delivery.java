package models.transaction.delivery;

import models.transaction.base.BaseTransaction;
import models.transaction.base.ITransactionItem;

import static constants.StringsConstants.*;

public class Delivery extends BaseTransaction {

    @Override
    public boolean addItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        if (item == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, DeliveryItem.class.getSimpleName()));
        }
        if (!(item instanceof DeliveryItem)) {
            throw new IllegalArgumentException(
                    String.format(ITEM_MUST_BE_INSTANCE_ADDING,
                            DeliveryItem.class.getSimpleName(),
                            Delivery.class.getSimpleName())
            );
        }
        return super.addItem(item);
    }

    @Override
    public boolean removeItem(ITransactionItem item) throws IllegalArgumentException, NullPointerException {
        if (item == null) {
            throw new NullPointerException(
                    String.format(CLASS_MUST_NOT_BE_NULL, DeliveryItem.class.getSimpleName()));
        }
        if (!(item instanceof DeliveryItem)) {
            throw new IllegalArgumentException(
                    String.format(ITEM_MUST_BE_INSTANCE_REMOVING,
                            DeliveryItem.class.getSimpleName(),
                            Delivery.class.getSimpleName())
            );
        }
        return super.removeItem(item);
    }
}
