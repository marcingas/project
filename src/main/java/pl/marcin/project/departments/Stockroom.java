package pl.marcin.project.departments;

import pl.marcin.project.model.Cup;
import pl.marcin.project.repository.CupsRepository;

import java.util.List;

public class Stockroom {
    CupsRepository cupsRepository;
    public String addCup(Cup cup) {
        cupsRepository.saveCup(cup);
        return "Cup " + cup.getId() + " saved";
    }

    public String updateCup(int cupId, Cup cup) {
        cupsRepository.updateCup(cupId,cup);
        return "Cup with id " + cupId + " updated";
    }

    public String deleteCus(int cupId) {
        cupsRepository.deleteCup(cupId);
        return "Cup with id " + cupId + " deleted";
    }

    public List<Cup> getAllCups() {
        return cupsRepository.findCups();
    }

    public Cup getCup(int cupId) {
        return cupsRepository.findCup(cupId);
    }


}
