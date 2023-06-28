package pl.marcin.project.repository;

import pl.marcin.project.model.Purchase;

import java.util.List;

public interface PurchaseRepository {
    void savePurchase(Purchase purchase);
    List<Purchase>findPurchaseByCustomerId(int id);
    List<Purchase> findAllPurchases();

    Purchase findPurchase(int id);
}
