package pl.marcin.project.entityService;

import jakarta.persistence.EntityNotFoundException;
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
        return cupEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cup Not Found"));

    }

    public CupEntity addCup(CupEntity cupEntity) {
        return cupEntityRepository.save(cupEntity);
    }

    public CupEntity updateCup(CupEntity cupEntity) {
        CupEntity existingCup = cupEntityRepository.findById(cupEntity.getCupId())
                .orElseThrow(() -> new EntityNotFoundException("Cup Not Found"));
        existingCup.setShape(cupEntity.getShape());
        existingCup.setColor(cupEntity.getColor());
        existingCup.setPrice(cupEntity.getPrice());
        return cupEntityRepository.save(existingCup);
    }

    public void deleteCup(Long id) {
        CupEntity existingCup = cupEntityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cup Not Found"));
        cupEntityRepository.delete(existingCup);
    }
}
