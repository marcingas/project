package pl.marcin.project.service;

import pl.marcin.project.model.Cup;
import pl.marcin.project.repository.CupRepository;

import java.util.List;

public class CupService {
    CupRepository cupRepository;

    public String addCup(Cup cup) {
        cupRepository.saveCup(cup);
        return "Cup " + cup.getId() + " saved";
    }

    public String updateCup(int cupId, Cup cup) {
        cupRepository.updateCup(cupId, cup);
        return "Cup with id " + cupId + " updated";
    }

    public String sellCup(int cupId) {
        cupRepository.deleteCup(cupId);
        return "Cup with id " + cupId + " deleted";
    }

    public List<Cup> showAllCups() {
        return cupRepository.findCups();
    }

    public Cup showCup(int cupId) {
        return cupRepository.findCup(cupId);
    }


}