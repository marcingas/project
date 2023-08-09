package pl.marcin.project.database;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import pl.marcin.project.entity.CupEntity;

public interface CupEntityReactiveRepository extends R2dbcRepository<CupEntity, Long> {
}
