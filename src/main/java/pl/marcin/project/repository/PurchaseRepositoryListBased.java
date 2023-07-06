package pl.marcin.project.repository;

import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseRepositoryListBased implements PurchaseRepository {
    private List<Purchase> purchases = new ArrayList<>();

    @Override
    public void savePurchase(Purchase purchase) {
        purchases.add(purchase);
        }

    @Override
    public List<Purchase> findPurchaseByCustomerId(int id) {
        List<Purchase> purchaseHistory = new ArrayList<>();
        purchaseHistory = purchases.stream()
                .filter(purchase -> purchase.getCustomer().getId() == id)
                .collect(Collectors.toList());
        if (purchaseHistory.isEmpty()) throw new RuntimeException("There is no such Purchase");

        return purchaseHistory;
    }

    @Override
    public List<Purchase> findAllPurchases() {
        return purchases;
    }

    @Override
    public Purchase findPurchase(int id) {
        return purchases.stream()
                .filter(purchase -> purchase.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no such Purchase"));
    }
}
