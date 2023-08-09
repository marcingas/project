package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.CustomerEntity;

@Repository
public interface CustomerEntityRepositoryReactive extends R2dbcRepository<CustomerEntity, Long> {
}
