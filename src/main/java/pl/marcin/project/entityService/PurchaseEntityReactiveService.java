package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.PurchaseEntityReactiveRepository;
import pl.marcin.project.entity.PurchaseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseEntityReactiveService {
    @Autowired
    private final PurchaseEntityReactiveRepository purchaseReactiveRepository;

    public PurchaseEntityReactiveService(PurchaseEntityReactiveRepository purchaseReactiveRepository) {
        this.purchaseReactiveRepository = purchaseReactiveRepository;
    }

    public Flux<PurchaseEntity> getAllPurchasees() {
        Flux<PurchaseEntity> fluxPurchases = purchaseReactiveRepository.findAll();
        return fluxPurchases;
    }

    public Mono<PurchaseEntity> getPurchaseById(long id) {
        Mono<PurchaseEntity> monoPurchasebyId = purchaseReactiveRepository.findById(id);
        return monoPurchasebyId;
    }

    public Mono<PurchaseEntity> savePurchase(Mono<PurchaseEntity> purchaseEntityMono) {
        return purchaseEntityMono.flatMap(purchaseReactiveRepository::save);
    }

    public Mono<PurchaseEntity> updatePurchase(Mono<PurchaseEntity> purchaseEntityMono) {
        return purchaseEntityMono.flatMap(purchaseReactiveRepository::save);
    }

    public Mono<Void> deletePurchase(Long id) {
        return purchaseReactiveRepository.deleteById(id);
    }

}
