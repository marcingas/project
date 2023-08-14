package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.PurchaseEntity;
import reactor.core.publisher.Flux;

@Repository
public interface PurchaseEntityReactiveRepository extends R2dbcRepository<PurchaseEntity, Long> {
    Flux<PurchaseEntity> findByCustomer_id(Long customerId);
}
