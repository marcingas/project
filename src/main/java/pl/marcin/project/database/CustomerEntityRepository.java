package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.entity.CustomerEntity;

public interface CustomerEntityRepository extends JpaRepository<CustomerEntity,Long> {
}
