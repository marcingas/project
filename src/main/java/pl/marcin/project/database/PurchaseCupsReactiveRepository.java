package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.PurchaseCups;
import reactor.core.publisher.Flux;

@Repository
public interface PurchaseCupsReactiveRepository extends R2dbcRepository<PurchaseCups, Long> {
    Flux<Long> findCupIdByPurchaseId(Long purchaseId);
}
