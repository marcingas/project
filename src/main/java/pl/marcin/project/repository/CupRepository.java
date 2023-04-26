package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.List;

public interface CupRepository {
//    save, update, delete, find, findAll, -> save do listy w impl
    String saveCup(Cup cup);

    void updateCup(int cupId, Cup cup);

    void deleteCup(int cupId);
    List<Cup>findCups();
    Cup findCup(int id);
}
