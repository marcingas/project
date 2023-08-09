package pl.marcin.project.database;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.CustomerEntity;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity,Long> {

}
