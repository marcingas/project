package pl.marcin.project.entityService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.entity.CupEntity;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CupEntityService {
    @Autowired
    private final CupEntityRepository cupEntityRepository;

    public List<CupEntity> getAllCups() {
        return cupEntityRepository.findAll();
    }

    public CupEntity getCupById(Long id) {
        Optional<CupEntity> cupEntity = cupEntityRepository.findById(id);
        if (cupEntity.isPresent()) return cupEntity.get();
        throw new RuntimeException("Cup not found with Id " + id);
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
