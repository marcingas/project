package pl.marcin.project.service;

import lombok.extern.slf4j.Slf4j;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.PurchaseRepository;

import java.util.List;

@Slf4j
public class PurchaseService {
    private PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public int savePurchase(Purchase purchase) {
        purchaseRepository.savePurchase(purchase);
        log.info("Purchase with cost:" + purchase.getPurchaseCost() + " saved");
        return 1;
    }

    public List<Purchase> purchaseHistoryByCustomerId(int id) {
        return purchaseRepository.findPurchaseByCustomerId(id);
    }
    public List<Purchase> AllPurchasesHistory(){
        return purchaseRepository.findAllPurchases();
    }
}
