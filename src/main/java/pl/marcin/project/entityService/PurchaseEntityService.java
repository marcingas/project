package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseEntityService {
    private final PurchaseEntityRepository purchaseEntityRepository;

    public List<PurchaseEntity> getAllPurchases() {
        return purchaseEntityRepository.findAll();
    }

    public PurchaseEntity getPurchase(Long id) {
        return purchaseEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No purchase with id " + id));
    }

    public CustomerEntity getCustomerByPurchaseId(Long purchaseId) {
        return purchaseEntityRepository.findCustomerByPurchaseId(purchaseId)
                .orElseThrow(() -> new NoSuchElementException("No such purchase with id " + purchaseId));
    }

    public List<PurchaseEntity> getPurchaseHistoryByCustomerId(Long customerId) {
        return purchaseEntityRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("no such customer or no such history for customer"));
    }

    public PurchaseEntity addPurchase(PurchaseEntity purchaseEntity) {
        return purchaseEntityRepository.save(purchaseEntity);
    }

    public PurchaseEntity updatePurchase(PurchaseEntity purchaseEntity) {
        PurchaseEntity existingPurchase = purchaseEntityRepository.findById(purchaseEntity.getPurchaseId()).
                orElseThrow(() -> new RuntimeException("No purchase found with id " + purchaseEntity.getPurchaseId()));
        existingPurchase.setCustomer(purchaseEntity.getCustomer());
        existingPurchase.setPurchaseCost(purchaseEntity.getPurchaseCost());
        existingPurchase.setCups(purchaseEntity.getCups());
        return purchaseEntityRepository.save(existingPurchase);
    }

    public void deletePurchase(Long id) {
        PurchaseEntity existingPurchase = purchaseEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No purchase with Id " + id));
        purchaseEntityRepository.delete(existingPurchase);
    }
}
