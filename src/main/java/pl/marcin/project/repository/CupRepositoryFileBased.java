package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CupRepositoryFileBased implements CupRepository {
    private List<Cup> cups = new ArrayList<>();

    @Override
    public void saveCup(Cup cup) {
        try{
            File file = new File("cups.txt");
            if(!file.exists()){
                file.createNewFile();
            }
        FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(cup.getId() + ","+ cup.getColor() + "," + cup.getShape() + "," +
                    cup.getPrice() + "\n");
            fileWriter.close();
    }catch (IOException e){
        e.getMessage();
        }
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
            if (searchedCup.getId() == cupId) {
                return searchedCup;
            } else {
                throw new RuntimeException("There is no such cup with id " + cupId);
            }
        }
        return null;
    }
}
