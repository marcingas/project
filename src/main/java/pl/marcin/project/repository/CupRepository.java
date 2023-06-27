package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.List;

public interface CupRepository {
    int saveCup(Cup cup);

    int updateCup(int cupId, Cup cup);

    boolean deleteCup(int cupId);

    List<Cup>findCups();

    Cup findCup(int id);
}
