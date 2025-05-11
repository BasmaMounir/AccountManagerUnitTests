package example.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountManagerTest {
    AccountManagerImpl accountManager;
    Customer customer ;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        accountManager = new AccountManagerImpl();
    }


    @Test
    void testDeposit(){
        int amount = 5000;
        accountManager.deposit(customer,amount);
        Assertions.assertEquals(5000,customer.getBalance());
    }

    @Test
    void testWithdraw(){
        //Arrange
        customer.setBalance(500);
        int amount = 100;

        //Act
        String result  = accountManager.withdraw(customer,amount);

        //Assert
        Assertions.assertEquals(400,customer.getBalance());
        Assertions.assertEquals("success",result);
    }

    @Test
    void givenCustomerBalanceAndCreditNotAllowed_whenWithdrawAnAmount_thenInsufficientAccountBalance(){
        //Arrange
        customer.setBalance(500);
        int amount = 600;

        //Act
        String result  = accountManager.withdraw(customer,amount);

        //Assert
        Assertions.assertEquals("insufficient account balance",result);
    }

    @Test
    void givenCustomerBalance_whenWithdrawAnAmount_thenMaximumCreditExceeded(){
        //Arrange
        customer.setBalance(1000);
        customer.setCreditAllowed(true);
        int amount = 2500;

        //Act
        String result  = accountManager.withdraw(customer,amount);

        //Assert
        Assertions.assertEquals("maximum credit exceeded",result);

    }

    @Test
    void givenCustomerBalanceAndLessThanMAX_CREDIT_whenWithdrawAnAmount_thenSuccess(){
        //Arrange
        customer.setBalance(1000);
        customer.setCreditAllowed(true);
        customer.setVip(true);

        int amount = 1050;

        //Act
        String result  = accountManager.withdraw(customer,amount);

        //Assert
        Assertions.assertEquals("success",result);

    }

    @Test
    void givenCustomerBalanceAndIsVip_whenWithdrawAnAmount_thenSuccess(){
        //Arrange
        customer.setBalance(1000);
        customer.setCreditAllowed(true);
        customer.setVip(true);
        int amount = 2500;

        //Act
        String result  = accountManager.withdraw(customer,amount);

        //Assert
        Assertions.assertEquals(-1500,customer.getBalance());
        Assertions.assertEquals("success",result);

    }

}
