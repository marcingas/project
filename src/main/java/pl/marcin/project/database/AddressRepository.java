package pl.marcin.project.database;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcin.project.entity.AddressEntity;

public interface AddressRepository extends JpaRepository <AddressEntity,Long> {
}