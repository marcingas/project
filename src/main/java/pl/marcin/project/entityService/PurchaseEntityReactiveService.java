package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityReactiveRepository;
import pl.marcin.project.database.PurchaseCupsReactiveRepository;
import pl.marcin.project.database.PurchaseEntityReactiveRepository;
import pl.marcin.project.entity.PurchaseCups;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Purchase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseEntityReactiveService {
    @Autowired
    private final PurchaseEntityReactiveRepository purchaseReactiveRepository;
    @Autowired
    private final CustomerEntityReactiveService customerEntityReactiveService;
    @Autowired
    private final PurchaseCupsReactiveRepository purchaseCupsReactiveRepository;
    @Autowired
    private final CupEntityReactiveService cupEntityReactiveService;


    public Flux<Purchase> getAllPurchases() {
        return purchaseReactiveRepository.findAll().map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Flux<Purchase> getPurchasesByCustomerId(Integer customerId) {
        return purchaseReactiveRepository.findByCustomer_id(customerId.longValue()).
                map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Mono<Purchase> getPurchaseById(Integer id) {
        return purchaseReactiveRepository.findById(id.longValue())
                .map(customerEntityReactiveService::purchaseEntityToDto);
    }


    public Mono<Purchase> savePurchase(Mono<Purchase> purchaseMono, List<Cup> cups) {

        return purchaseMono.map(customerEntityReactiveService::dtoToPurchaseEntity)
                .flatMap(purchaseEntity -> {
                            purchaseEntity.setCups(cups);
                            return Flux.fromIterable(cups)
                                    .flatMap(cup -> {
                                        return purchaseCupsReactiveRepository.save(
                                                new PurchaseCups(purchaseEntity.getId(), cup.getCup_id().longValue()));
                                    }).then(Mono.just(purchaseEntity));
                        }
                )
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
