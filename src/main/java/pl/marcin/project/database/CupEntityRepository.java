package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.entity.CupEntity;

public interface CupEntityRepository extends JpaRepository<CupEntity,Long> {
}
