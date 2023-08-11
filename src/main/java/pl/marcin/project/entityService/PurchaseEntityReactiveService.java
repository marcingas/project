package pl.marcin.project.entityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.PurchaseEntityReactiveRepository;
import pl.marcin.project.model.Purchase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseEntityReactiveService {
    @Autowired
    private final PurchaseEntityReactiveRepository purchaseReactiveRepository;
    @Autowired
    private final CustomerEntityReactiveService customerEntityReactiveService;

    public PurchaseEntityReactiveService(PurchaseEntityReactiveRepository purchaseReactiveRepository,
                                         CustomerEntityReactiveService customerEntityReactiveService) {
        this.purchaseReactiveRepository = purchaseReactiveRepository;
        this.customerEntityReactiveService = customerEntityReactiveService;
    }

    public Flux<Purchase> getAllPurchases() {
        return purchaseReactiveRepository.findAll().map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Mono<Purchase> getPurchaseById(Integer id) {
        return purchaseReactiveRepository.findById(id.longValue())
                .map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Mono<Purchase> savePurchase(Mono<Purchase> purchaseMono) {
        return purchaseMono.map(customerEntityReactiveService::dtoToPurchaseEntity)
                .flatMap(purchaseReactiveRepository::save)
                .map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Mono<Purchase> updatePurchase(Mono<Purchase> purchaseMono, Integer id) {
        return purchaseReactiveRepository.findById(id.longValue())
                .flatMap(purchaseEntity -> purchaseMono.map(customerEntityReactiveService::dtoToPurchaseEntity))
                .doOnNext(purchaseEntity -> purchaseEntity.setId(id.longValue()))
                .flatMap(purchaseReactiveRepository::save)
                .map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Mono<Void> deletePurchase(Integer id) {
        return purchaseReactiveRepository.deleteById(id.longValue());
    }

}
