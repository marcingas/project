package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.entity.PurchaseEntity;

public interface PurchaseEntityRepository extends JpaRepository<PurchaseEntity,Long> {
}
