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

//    @Test
//    public void shouldReturnCupById() {
//        //given
//        int id = 1;
//        Cup expectedCup = Cup.builder().id(id).build();
//        cupRepository.saveCup(expectedCup);
//
//        //when
//        Cup cup = cupRepository.findCup(id);
//
//        //then
//        Assertions.assertEquals(id, cup.getId());
//    }

//    @Test
//    public void shouldReturnCupWithId2() {
//        //given
//        int id = 1;
//        int id2 = 2;
//        Cup expectedCup1 = Cup.builder().id(id).build();
//        Cup expectedCup2 = Cup.builder().id(id2).build();
//        cupRepository.saveCup(expectedCup1);
//        cupRepository.saveCup(expectedCup2);
//
//        //when
//        Cup cup = cupRepository.findCup(id2);
//
//        //then
//        Assertions.assertEquals(id2, cup.getId());
//    }

//    @Test
//    public void shouldThrowExceptionIfNotFindCupById() {
//        //given
//        int id = 1;
//        int id2 = 2;
//        //when:
//        Cup cup2 = Cup.builder().id(id2).build();
//        cupRepository.saveCup(cup2);
//
//        //then:
//        assertThrows(RuntimeException.class, () -> {
//            cupRepository.findCup(id);
//        });
//    }

//    @Test
//    public void shouldThrowExceptionMessageNoSuchCupById() {
//        //given
//        int id = 1;
//        int id2 = 2;
//        Cup expectedCup2 = Cup.builder().id(id2).build();
//        cupRepository.saveCup(expectedCup2);
//        //when
//        Throwable exception = assertThrows(RuntimeException.class, () -> {
//            cupRepository.findCup(id);
//        });
//
//        //then
//        Assertions.assertEquals("There is no cup with this id", exception.getMessage());
//    }

//    @Test
//    void shouldEqualFindPurchaseWhenSavePurchase() {
//        int customerId = 1;
//        int purchaseId = 1;
//        Customer customer = Customer.builder().id(customerId).build();
//        Purchase purchase = Purchase.builder()
//                .id(purchaseId)
//                .customer(customer)
//                .purchaseCost(BigDecimal.valueOf(12.34))
//                .build();
//        //when
//        purchaseRepository.savePurchase(purchase);
//
//        //then
//        Assertions.assertEquals(purchase,purchaseRepository.findPurchase(purchaseId));
//    }

//    @Test
//    void shouldEqualExpectedlistWhenFindPurchaseByCustomerId() {
//        //given
//        int id = 1;
//        int id2 = 2;
//        Customer customer = Customer.builder().id(id).build();
//        Customer customer2 = Customer.builder().id(id2).build();
//        Purchase purchase = Purchase.builder()
//                .customer(customer)
//                .purchaseCost(BigDecimal.valueOf(12.34))
//                .build();
//        Purchase purchase2 = Purchase.builder()
//                .customer(customer2)
//                .purchaseCost(BigDecimal.valueOf(12.31))
//                .build();
//        purchaseRepository.savePurchase(purchase);
//        purchaseRepository.savePurchase(purchase2);
//
//        List<Purchase>expectedPurchaseList = new ArrayList<>();
//        expectedPurchaseList.add(purchase);
//
//        //when
//        List<Purchase>purchaseList = purchaseRepository.findPurchaseByCustomerId(id);
//
//        //then
//        Assertions.assertEquals(expectedPurchaseList,purchaseList );
//    }

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