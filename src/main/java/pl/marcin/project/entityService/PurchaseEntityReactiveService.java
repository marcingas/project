package pl.marcin.project.entityService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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


    public Flux<Purchase> getAllPurchases() {
        return purchaseReactiveRepository.findAll().map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Flux<Purchase> getCustomersPurchaseHistory(Integer customerId) {
        return purchaseReactiveRepository.findByCustomerId(customerId.longValue())
                .map(customerEntityReactiveService::purchaseEntityToDto);
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
                                                new PurchaseCups(purchaseEntity.getId(), cup.getCupId().longValue()));
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

    public Mono<Purchase> updatePurchaseCupList(Mono<Purchase> purchaseMono, Integer id, List<Cup> updatedCupsList) {
        return purchaseReactiveRepository.findById(id.longValue())
                .flatMap(purchaseEntity -> purchaseMono.map(customerEntityReactiveService::dtoToPurchaseEntity))
                .doOnNext(purchaseEntity -> purchaseEntity.setId(id.longValue()))
                .flatMap(purchaseEntity -> {
                            purchaseEntity.setCups(updatedCupsList);
                            return Flux.fromIterable(updatedCupsList)
                                    .flatMap(cup -> {
                                        return purchaseCupsReactiveRepository.save(
                                                new PurchaseCups(purchaseEntity.getId(), cup.getCupId().longValue()));
                                    }).then(Mono.just(purchaseEntity));
                        }
                )
                .map(customerEntityReactiveService::purchaseEntityToDto);
    }

    public Mono<Void> deletePurchase(Integer purchaseId) {
        return purchaseCupsReactiveRepository.deleteByPurchaseId(purchaseId.longValue())
                .then(purchaseReactiveRepository.deleteById(purchaseId.longValue()));
    }
}
