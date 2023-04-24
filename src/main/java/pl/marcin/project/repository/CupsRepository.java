package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.util.List;

public interface CupsRepository {
//    save, update, delete, find, findAll, -> save do listy w impl
    public String saveCup(Cup cup);

    String updateCup(int cupId, Cup cup);

    public void deleteCup(int cupId);
    public List<Cup>findCups();
    public Cup findCup(int id);
}
