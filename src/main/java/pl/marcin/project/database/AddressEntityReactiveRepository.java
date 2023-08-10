package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.AddressEntity;

@Repository
public interface AddressEntityReactiveRepository extends R2dbcRepository<AddressEntity, Long> {

}
