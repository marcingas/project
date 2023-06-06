package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CupEntityService {
    private final CupEntityRepository cupEntityRepository;

    public List<CupEntity> getAllCups() {
        return cupEntityRepository.findAll();
    }

    public CupEntity getCups(Long id) {
        Optional<CupEntity> cupEntity = cupEntityRepository.findById(id);
        if (cupEntity.isPresent()) {
            return cupEntity.get();
        } else {
            throw new RuntimeException("Cup not found by id: " + id);
        }
    }

    public CupEntity addCup(CupEntity cupEntity) {
        return cupEntityRepository.save(cupEntity);
    }

    public CupEntity updateCup(CupEntity cupEntity) {
        return cupEntityRepository.save(cupEntity);
    }

    public void deleteCup(Long id) {
        cupEntityRepository.deleteById(id);
    }

}
