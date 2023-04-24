package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Purchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepositoryListBased implements PurchaseRepository {
    private List<Purchase> purchases = new ArrayList<>();

    @Override
    public String savePurchase(Purchase purchase) {
        purchases.add(purchase);
        return "Purchase saved";
    }

    @Override
    public List<Purchase> getAllPurchase() {
        return purchases;
    }

    @Override
    public List<Purchase> findPurchaseByCustomer(int customerId) {
        List<Purchase> temporaryList = new ArrayList<>();
        for (Purchase searchedPurchase : purchases) {
            if (searchedPurchase.getCustomer().getId() == customerId) {
                temporaryList.add(searchedPurchase);
            }
        }
        return temporaryList;
    }
}
