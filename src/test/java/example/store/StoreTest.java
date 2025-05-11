package example.store;

import example.account.AccountManager;
import example.account.AccountManagerImpl;
import example.account.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


public class StoreTest {
    AccountManager accountManager = mock(AccountManagerImpl.class);
    Product product;
    Customer customer;
    Store store = new StoreImpl(accountManager);

    @BeforeEach
    void setUp() {
        product = new Product();
        customer = new Customer();
    }

    @Test
    void givenAvailableProductAndPaymentSuccess_whenBuy_thenQuantityDecreases(){
        //arrange
        product.setQuantity(5);
        when(accountManager.withdraw(any(),anyInt())).thenReturn("success");

        //act
        store.buy(product,customer);

        //assert
        Assertions.assertEquals(4,product.getQuantity());
    }

    @Test
    void givenProductWithZeroQuantity_whenBuy_thenProductOutOfStockException() {
        //arrange
        product.setQuantity(0);

        //assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,() -> {
            //act
            store.buy(product, customer);
        });

        Assertions.assertTrue(exception.getMessage().contains("Product out of stock"));

    }

    @Test
    void givenAvailableProductAndWithdrawIsFailed_whenBuy_thenPaymentFailureException() {
        //arrange
        product.setQuantity(10);
        when(accountManager.withdraw(any(),anyInt())).thenReturn("fail");

        //assert
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,() -> {
            //act
            store.buy(product, customer);
        });

        Assertions.assertTrue(exception.getMessage().contains("Payment failure"));

    }
}
