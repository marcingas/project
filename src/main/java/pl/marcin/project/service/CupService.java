package pl.marcin.project.service;

import pl.marcin.project.model.Cup;
import pl.marcin.project.repository.CupRepository;
import pl.marcin.project.repository.CupRepositoryFileBased;
import pl.marcin.project.repository.CupRepositoryListBased;

import java.util.List;

public class CupService {
    private CupRepository cupRepository;
    private CupRepositoryFileBased cupRepositoryFileBased;
    public CupService(CupRepositoryFileBased cupRepositoryFileBased) {
        this.cupRepositoryFileBased = cupRepositoryFileBased;
    }
    public CupService(CupRepository cupRepository) {
        this.cupRepository = cupRepository;
    }

    public String addCup(Cup cup) {
        cupRepository.saveCup(cup);
        return "Cup " + cup.getId() + " saved";
    }
    public String addCupToFile(Cup cup){
        cupRepositoryFileBased.saveCup(cup);
        return "Cup added to file";
    }

    public String updateCup(int cupId, Cup cup) {
        cupRepository.updateCup(cupId, cup);
        return "Cup with id " + cupId + " updated";
    }

    public String sellCup(Cup cup) {
        cupRepository.deleteCup(cup);
        return "Cup with id " + cup + " deleted";
    }

    public List<Cup> showAllCups() {
        return cupRepository.findCups();
    }

    public Cup showCup(int cupId) {
        return cupRepository.findCup(cupId);
    }


}
