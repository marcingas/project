package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PurchaseRepositoryListBasedTest {
    private int notExistingPurchaseId = 1;
    private int expPurchaseId = 2;
    private int expPurchaseId2 = 3;
    private Purchase expPurchase = Purchase.builder().id(expPurchaseId).purchaseCost(BigDecimal.valueOf(12.34)).build();
    private Purchase expPurchase2 = Purchase.builder().id(expPurchaseId2).purchaseCost(BigDecimal.valueOf(5)).build();
    private PurchaseRepository purchaseRepository = new PurchaseRepositoryListBased();

    @Test
    public void shouldReturnPurchaseById() {

        //given
        purchaseRepository.savePurchase(expPurchase);

        //when
        Purchase purchase = purchaseRepository.findPurchase(expPurchaseId);

        //then
        Assertions.assertEquals(expPurchaseId, purchase.getId());
    }

    @Test
    public void shouldThrowExceptionIfNotFindPurchaseById() {

        //given
        purchaseRepository.savePurchase(expPurchase);

        //then
        var exception = assertThrows(RuntimeException.class, () -> {
            purchaseRepository.findPurchase(notExistingPurchaseId);
        });
        Assertions.assertEquals("There is no such Purchase", exception.getMessage());
    }

    @Test
    void shouldEqualFindPurchaseWhenSavePurchase() {

        //when
        purchaseRepository.savePurchase(expPurchase);

        //then
        Assertions.assertEquals(expPurchase, purchaseRepository.findPurchase(expPurchaseId));
    }

    @Test
    void shouldEqualExpectedlistWhenFindPurchaseByCustomerId() {

        //given
        int customerId = 1;
        int customerId2 = 2;
        Customer expectedCustomer = Customer.builder().id(customerId).build();
        Customer customer2 = Customer.builder().id(customerId2).build();

        Purchase expectedCustomersPurchase = Purchase.builder()
                .customer(expectedCustomer)
                .purchaseCost(BigDecimal.valueOf(12.34))
                .build();

        Purchase Customers2Purchase = Purchase.builder()
                .customer(customer2)
                .purchaseCost(BigDecimal.valueOf(12.31))
                .build();

        purchaseRepository.savePurchase(expectedCustomersPurchase);
        purchaseRepository.savePurchase(Customers2Purchase);

        List<Purchase> expectedPurchaseList = new ArrayList<>();
        expectedPurchaseList.add(expectedCustomersPurchase);

        //when
        List<Purchase> searchedPurchaseList = purchaseRepository.findPurchaseByCustomerId(customerId);

        //then
        Assertions.assertEquals(expectedPurchaseList, searchedPurchaseList);
    }

    @Test
    void shouldEqualListOfAllPurchasesIfFindAllPurchases() {
        //given
        int id = 1;
        int id2 = 2;
        Customer customer = Customer.builder().id(id).build();
        Customer customer2 = Customer.builder().id(id2).build();
        Purchase purchase = Purchase.builder()
                .customer(customer)
                .purchaseCost(BigDecimal.valueOf(12.34))
                .build();
        Purchase purchase2 = Purchase.builder()
                .customer(customer2)
                .purchaseCost(BigDecimal.valueOf(12.31))
                .build();
        purchaseRepository.savePurchase(purchase);
        purchaseRepository.savePurchase(purchase2);

        List<Purchase> expectedPurchaseList = new ArrayList<>();
        expectedPurchaseList.add(purchase);
        expectedPurchaseList.add(purchase2);

        //when
        List<Purchase> purchaseList = purchaseRepository.findAllPurchases();

        //then
        Assertions.assertEquals(expectedPurchaseList, purchaseList);
    }
}