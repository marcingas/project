package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.PurchaseEntity;
@Repository
public interface PurchaseEntityRepository extends JpaRepository<PurchaseEntity,Long> {
}
