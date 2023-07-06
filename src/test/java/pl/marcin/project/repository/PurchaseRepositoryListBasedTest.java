package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseRepositoryListBasedTest {
    PurchaseRepository purchaseRepository = new PurchaseRepositoryListBased();

    @Test
    public void shouldReturnPurchaseById() {
        //given
        int id = 1;
        Purchase expectedPurchase = Purchase.builder().id(id).build();
        purchaseRepository.savePurchase(expectedPurchase);

        //when
        Purchase purchase = purchaseRepository.findPurchase(id);

        //then
        Assertions.assertEquals(id, purchase.getId());
    }

    @Test
    public void shouldReturnPurchaseWithId2() {
        //given
        int id = 1;
        int id2 = 2;
        Purchase expectedPurchase1 = Purchase.builder().id(id).build();
        Purchase expectedPurchase2 = Purchase.builder().id(id2).build();
        purchaseRepository.savePurchase(expectedPurchase1);
        purchaseRepository.savePurchase(expectedPurchase2);

        //when
        Purchase purchase = purchaseRepository.findPurchase(2);

        //then
        Assertions.assertEquals(id2, purchase.getId());
    }

    @Test
    public void shouldThrowExceptionIfNotFindPurchaseById() {
        //given
        int id = 1;
        int id2 = 2;
        //when:
        Purchase expectedPurchase1 = Purchase.builder().id(id).build();
        purchaseRepository.savePurchase(expectedPurchase1);

        //then:
        assertThrows(RuntimeException.class, () -> {
            purchaseRepository.findPurchase(id2);
        });
    }

    @Test
    public void shouldThrowExceptionMessageNoSuchPurchaseById() {
        //given
        int id = 1;
        int id2 = 2;
        //when:
        Purchase expectedPurchase1 = Purchase.builder().id(id).build();
        purchaseRepository.savePurchase(expectedPurchase1);
        //when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            purchaseRepository.findPurchase(id2);
        });

        //then
        Assertions.assertEquals("There is no such Purchase", exception.getMessage());
    }

    @Test
    void shouldEqualFindPurchaseWhenSavePurchase() {
        int customerId = 1;
        int purchaseId = 1;
        Customer customer = Customer.builder().id(customerId).build();
        Purchase purchase = Purchase.builder()
                .id(purchaseId)
                .customer(customer)
                .purchaseCost(BigDecimal.valueOf(12.34))
                .build();
        //when
        purchaseRepository.savePurchase(purchase);

        //then
        Assertions.assertEquals(purchase, purchaseRepository.findPurchase(purchaseId));
    }

    @Test
    void shouldEqualExpectedlistWhenFindPurchaseByCustomerId() {
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

        //when
        List<Purchase> purchaseList = purchaseRepository.findPurchaseByCustomerId(id);

        //then
        Assertions.assertEquals(expectedPurchaseList, purchaseList);
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