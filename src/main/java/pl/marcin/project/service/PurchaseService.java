package pl.marcin.project.service;

import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.PurchaseRepository;

import java.util.List;

public class PurchaseService {
    private PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public String savePurchase(Purchase purchase) {
        purchaseRepository.savePurchase(purchase);
        return "Customer " + purchase.getCustomer().getName() + "'s purchase saved";
    }

    public List<Purchase> purchaseHistoryByCustomerId(int id) {
        return purchaseRepository.findPurchaseByCustomerId(id);
    }
}
