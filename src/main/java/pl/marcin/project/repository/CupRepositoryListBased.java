package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.ArrayList;
import java.util.List;

public class CupRepositoryListBased implements CupRepository {
    private List<Cup> cups = new ArrayList<>();

    @Override
    public void saveCup(Cup cup) {
        cups.add(cup);
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
        Cup cup = null;
        for (Cup searchedCup : cups) {
            if (searchedCup.getId() == cupId) {
                cup = searchedCup;
            }
        }
        if(cup == null){
            throw new RuntimeException("There is no cup with this id");
        }else{
        return cup;
        }
    }
}
