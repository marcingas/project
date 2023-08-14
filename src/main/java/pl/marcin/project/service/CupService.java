package pl.marcin.project.service;

import lombok.extern.slf4j.Slf4j;
import pl.marcin.project.model.Cup;
import pl.marcin.project.repository.CupRepository;
import pl.marcin.project.repository.CupRepositoryFileBased;

import java.util.List;

@Slf4j
public class CupService {
    private CupRepository cupRepository;
    private CupRepositoryFileBased cupRepositoryFileBased;
    public CupService(CupRepositoryFileBased cupRepositoryFileBased) {
        this.cupRepositoryFileBased = cupRepositoryFileBased;
    }
    public CupService(CupRepository cupRepository) {
        this.cupRepository = cupRepository;
    }

    public int addCup(Cup cup) {
        cupRepository.saveCup(cup);
        log.info("Cup " + cup.getCupId() + " saved");
        return cup.getCupId();
    }

    public int addCupToFile(Cup cup) {
        cupRepositoryFileBased.saveCup(cup);
        log.info("Cup " + cup.getCupId() + " saved");
        return cup.getCupId();
    }

    public int updateCup(int cupId, Cup cup) {
        cupRepository.updateCup(cupId, cup);
        log.info("Cup with id " + cupId + " updated");
        return cup.getCupId();
    }

    public int deleteCup(int cupId) {
        cupRepository.deleteCup(cupId);
        log.info("Cup with id " + cupId + " deleted");
        return -1;
    }

    public List<Cup> showAllCups() {
        return cupRepository.findCups();
    }

    public Cup showCup(int cupId) {
        return cupRepository.findCup(cupId);
    }


}
