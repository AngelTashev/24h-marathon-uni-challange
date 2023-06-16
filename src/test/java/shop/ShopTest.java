package shop;

import models.basic.CashDesk;
import models.basic.Cashier;
import models.basic.Receipt;
import models.basic.Shop;
import models.transaction.delivery.Delivery;
import models.transaction.sale.Sale;
import models.transaction.store.StoreItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ShopTest {

    Cashier realCashier;
    CashDesk realCashDesk;

    @Mock
    CashDesk cashDesk;
    @Mock
    Cashier cashier;
    @Mock
    Delivery delivery;
    @Mock
    Sale sale;
    @Mock
    Receipt receipt;
    @Mock
    StoreItem storeItem;

    /* CashDesk */
    @Test
    public void testAddCashDesk() {
        // Arrange
        Shop shop = new Shop();

        // Act
        shop.addCashDesk(cashDesk);

        // Assert
        assertEquals(1, shop.getCashDesks().size());
    }

    @Test
    public void testAddCashDeskShouldThrowOnNull() {
        // Arrange
        Shop shop = new Shop();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> shop.addCashDesk(null));
    }

    @Test
    public void testAddCashDeskShouldThrowIfItAlreadyExists() {
        // Arrange
        Shop shop = new Shop();

        // Act
        shop.addCashDesk(cashDesk);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> shop.addCashDesk(cashDesk));
    }

    @Test
    public void testRemoveCashDesk() {
        Shop shop = new Shop();

        shop.addCashDesk(cashDesk);
        assertEquals(1, shop.getCashDesks().size());

        shop.removeCashDesk(cashDesk);
        assertEquals(0, shop.getCashDesks().size());
    }

    @Test
    public void testRemoveCashDeskShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.removeCashDesk(null));
    }

    /* Cashier */

    @Test
    public void testAddCashier() {
        Shop shop = new Shop();

        shop.addCashier(cashier);
        assertEquals(1, shop.getCashiers().size());
    }

    @Test
    public void testAddCashierShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.addCashier(null));
    }

    @Test
    public void testAddCashierShouldThrowIfItAlreadyExists() {
        Shop shop = new Shop();

        shop.addCashier(cashier);
        assertThrows(IllegalArgumentException.class, () -> shop.addCashier(cashier));
    }

    @Test
    public void testRemoveCashier() {
        Shop shop = new Shop();

        shop.addCashier(cashier);
        assertEquals(1, shop.getCashiers().size());

        shop.removeCashier(cashier);
        assertEquals(0, shop.getCashiers().size());
    }

    @Test
    public void testRemoveCashierShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.removeCashier(null));
    }

    /* Delivery */

    @Test
    public void testAddDelivery() {
        Shop shop = new Shop();

        shop.addDelivery(delivery);
        assertEquals(1, shop.getDeliveries().size());
    }

    @Test
    public void testAddDeliveryShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.addDelivery(null));
    }

    @Test
    public void testRemoveDelivery() {
        Shop shop = new Shop();

        shop.addDelivery(delivery);
        assertEquals(1, shop.getDeliveries().size());

        shop.removeDelivery(delivery);
        assertEquals(0, shop.getDeliveries().size());
    }

    @Test
    public void testRemoveDeliveryShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.removeDelivery(null));
    }

    /* Sale */

    @Test
    public void testAddSale() {
        Shop shop = new Shop();

        shop.addSale(sale);
        assertEquals(1, shop.getSales().size());
    }

    @Test
    public void testAddSaleShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.addSale(null));
    }

    @Test
    public void testRemoveSale() {
        Shop shop = new Shop();

        shop.addSale(sale);
        assertEquals(1, shop.getSales().size());

        shop.removeSale(sale);
        assertEquals(0, shop.getSales().size());
    }

    @Test
    public void testRemoveSaleShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.removeSale(null));
    }

    /* Receipt */

    @Test
    public void testAddReceipt() {
        Shop shop = new Shop();

        shop.addReceipt(receipt);
        assertEquals(1, shop.getReceipts().size());
    }

    @Test
    public void testAddReceiptShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.addReceipt(null));
    }

    @Test
    public void testRemoveReceipt() {
        Shop shop = new Shop();

        shop.addReceipt(receipt);
        assertEquals(1, shop.getReceipts().size());

        shop.removeReceipt(receipt);
        assertEquals(0, shop.getReceipts().size());
    }

    @Test
    public void testRemoveReceiptShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.removeReceipt(null));
    }

    @Test
    public void testGenerateReceipt() {
        Shop shop = new Shop();
        realCashier = new Cashier("Pepa", 1, 3000);
        realCashDesk = new CashDesk(1, realCashier);
        shop.generateReceipt(sale, realCashDesk);

        assertEquals(1, shop.getReceipts().size());
    }

    /* StoreItem */

    @Test
    public void testAddItemToStore() {
        Shop shop = new Shop();

        shop.addItemToStore(storeItem);
        assertEquals(1, shop.getItemsInStore().size());
    }

    @Test
    public void testAddItemToStoreShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.addItemToStore(null));
    }

    @Test
    public void testRemoveItemFromStore() {
        Shop shop = new Shop();

        shop.addItemToStore(storeItem);
        assertEquals(1, shop.getItemsInStore().size());

        shop.removeItemFromStore(storeItem);
        assertEquals(0, shop.getItemsInStore().size());
    }

    @Test
    public void testRemoveItemFromStoreShouldThrowOnNull() {
        Shop shop = new Shop();

        assertThrows(NullPointerException.class, () -> shop.removeItemFromStore(null));
    }

}
