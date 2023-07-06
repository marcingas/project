package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.ArrayList;
import java.util.List;

public class CupRepositoryListBased implements CupRepository {
    private List<Cup> cups = new ArrayList<>();

    @Override
    public int saveCup(Cup cup) {
        cups.add(cup);
        return cup.getId();
    }

    @Override
    public int updateCup(int cupId, Cup cup) {
        for (var searchedCup : cups) {
            if (searchedCup.getId() == cupId) {
                searchedCup.setColor(cup.getColor());
                searchedCup.setShape(cup.getShape());
                searchedCup.setPrice(cup.getPrice());
                return cupId;
            }
        }
        throw new RuntimeException("There is no cup with id: " + cupId);
    }

    @Override
    public boolean deleteCup(int cupId) {
        return cups.removeIf(s -> s.getId() == cupId);

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
        if (cup == null) {
            throw new RuntimeException("There is no cup with this id");
        } else {
            return cup;
        }
    }
}
