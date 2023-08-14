package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.ArrayList;
import java.util.List;

public class CupRepositoryListBased implements CupRepository {
    private List<Cup> cups = new ArrayList<>();

    @Override
    public int saveCup(Cup cup) {
        cups.add(cup);
        return cup.getCupId();
    }

    @Override
    public int updateCup(int cupId, Cup cup) {
        Cup foundCup = cups.stream()
                .filter(searchedCup -> searchedCup.getCupId() == cupId)
                .findFirst()
                .orElse(null);

        if (foundCup != null) {
            foundCup.setColor(cup.getColor());
            foundCup.setShape(cup.getShape());
            foundCup.setPrice(cup.getPrice());
        } else {
            throw new RuntimeException("Cup not found");
        }
        return cupId;
    }

    @Override
    public boolean deleteCup(int cupId) {
        return cups.removeIf(s -> s.getCupId() == cupId);

    }

    @Override
    public List<Cup> findCups() {
        return cups;
    }

    @Override
    public Cup findCup(int cupId) {
        Cup cup = null;
        for (Cup searchedCup : cups) {
            if (searchedCup.getCupId() == cupId) {
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
