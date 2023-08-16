package pl.marcin.project.database;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {

}
