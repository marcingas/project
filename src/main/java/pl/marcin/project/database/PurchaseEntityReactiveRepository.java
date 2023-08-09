package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.PurchaseEntity;

@Repository
public interface PurchaseEntityReactiveRepository extends R2dbcRepository<PurchaseEntity, Long> {
}
