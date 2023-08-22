package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseEntityRepository extends JpaRepository<PurchaseEntity, Long> {
    Optional<List<PurchaseEntity>> findByCustomerCustomerId(Long customerId);

    @Query("SELECT p.customer FROM PurchaseEntity p WHERE p.purchaseId = :purchaseId")
    Optional<CustomerEntity> findCustomerByPurchaseId(@Param("purchaseId") Long purchaseId);
}
