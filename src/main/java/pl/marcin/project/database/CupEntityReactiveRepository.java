package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.CupEntity;
import reactor.core.publisher.Flux;

@Repository
public interface CupEntityReactiveRepository extends R2dbcRepository<CupEntity, Long> {

}
