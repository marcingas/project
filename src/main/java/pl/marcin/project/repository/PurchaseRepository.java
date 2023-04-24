package pl.marcin.project.repository;

import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.util.List;

public interface PurchaseRepository {
    public String savePurchase(Purchase purchase);
    public List<Purchase> getAllPurchase();
    public List<Purchase> findPurchaseByCustomer(int customerId);
}
