package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.ArrayList;
import java.util.List;

public class CupRepositoryListBased implements CupRepository {
    private List<Cup> cups = new ArrayList<>();

    @Override
    public String saveCup(Cup cup) {
        cups.add(cup);
        return "Cup: " + cup + " added";
    }

    @Override
    public void updateCup(int cupId, Cup cup) {
        for (Cup element : cups) {
            if (element.getId() == cupId) {
                cups.add(cup);
            } else {
                throw new RuntimeException("There is no cup with id: " + cupId);
            }
        }

    }

    @Override
    public void deleteCup(Cup cup) {
        cups.removeIf(s -> s.equals(cup));

    }

    @Override
    public List<Cup> findCups() {
        return cups;
    }

    @Override
    public Cup findCup(int cupId) {
        for (Cup searchedCup : cups) {
            if (searchedCup.getId()==cupId) {
                return searchedCup;
            } else {
                throw new RuntimeException("There is no such cup with id " + cupId );
            }
        }
        return null;
    }
}
