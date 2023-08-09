package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.marcin.project.entity.AddressEntity;

public interface AddressEntityRepository extends JpaRepository <AddressEntity,Long> {
}
