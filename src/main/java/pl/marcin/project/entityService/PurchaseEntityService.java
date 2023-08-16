package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseEntityService {
    private final PurchaseEntityRepository purchaseEntityRepository;

    public List<PurchaseEntity> getAllPurchases() {
        return purchaseEntityRepository.findAll();
    }

    public PurchaseEntity getPurchase(Long id) {
        Optional<PurchaseEntity> purchaseEntity = purchaseEntityRepository.findById(id);
        if (purchaseEntity.isPresent()) {
            return purchaseEntity.get();
        } else {
            throw new RuntimeException("Purchase not found by id: " + id);
        }
    }

    public List<PurchaseEntity> getPurchaseHistoryByCustomerId(Long customerId) {
        Optional<List<PurchaseEntity>> purchases = purchaseEntityRepository.findByCustomerCustomerId(customerId);
        if (purchases.isPresent()) {
            return purchases.get();
        } else {
            throw new RuntimeException("There is no Purchase history yet");
        }
    }

    public PurchaseEntity addPurchase(PurchaseEntity purchaseEntity) {
        return purchaseEntityRepository.save(purchaseEntity);
    }

    public PurchaseEntity updatePurchase(PurchaseEntity purchaseEntity) {
        return purchaseEntityRepository.save(purchaseEntity);
    }

    public void deletePurchase(Long id) {
        purchaseEntityRepository.deleteById(id);
    }
}
