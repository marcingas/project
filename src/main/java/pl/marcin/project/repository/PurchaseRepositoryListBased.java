package pl.marcin.project.repository;

import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepositoryListBased implements PurchaseRepository {
    private List<Purchase> purchases = new ArrayList<>();

    @Override
    public void savePurchase(Purchase purchase) {
        purchases.add(purchase);
        }

    @Override
    public List<Purchase> findPurchaseByCustomerId(int id) {
        List<Purchase> purchaseHistory = new ArrayList<>();
        for (Purchase purchase : purchases) {
            if (purchase.getCustomer().getId() == id) {
                purchaseHistory.add(purchase);
            }
        }
        return purchaseHistory;
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchases;
    }
}
