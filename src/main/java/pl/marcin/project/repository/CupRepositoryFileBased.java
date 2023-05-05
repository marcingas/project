package pl.marcin.project.repository;

import pl.marcin.project.model.Cup;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class CupRepositoryFileBased implements CupRepository {
    private List<Cup> cups = new ArrayList<>();

    @Override
    public void saveCup(Cup cup) {
        try {
            File file = new File("cups.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(cup.getId() + "," + cup.getColor() + "," + cup.getShape() + "," +
                    cup.getPrice() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @Override
    public void updateCup(int cupId, Cup cup) {
        File tempFile = new File("cupstemp.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("cups.txt"));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String[] cupData = scanner.nextLine().split(",");
                int id = Integer.parseInt(cupData[0]);
                String color = cupData[1];
                String shape = cupData[2];
                double price = Double.parseDouble(cupData[3]);

                FileWriter fileWriter = new FileWriter(tempFile, true);
                if (id == cupId) {
                    fileWriter.write(cup.getId() + "," + cup.getColor() + "," + cup.getShape() + "," +
                            cup.getPrice() + "\n");
                } else {
                    fileWriter.write(id + "," + color + "," + shape + "," +
                            price + "\n");
                }
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }


    @Override
    public void deleteCup(Cup cup) {
        cups.removeIf(s -> s.equals(cup));
    }

    @Override
    public List<Cup> findCups() {
        List<Cup> cups = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("cups.txt"));
            while (scanner.hasNextLine()) {
                String[] cupData = scanner.nextLine().split(",");
                int id = Integer.parseInt(cupData[0]);
                String color = cupData[1];
                String shape = cupData[2];
                double price = Double.parseDouble(cupData[3]);

                Cup cup = new Cup(id, color, shape, BigDecimal.valueOf(price));
                cups.add(cup);
            }

        } catch (IOException e) {

        }

        return cups;
    }

    @Override
    public Cup findCup(int cupId) {
        Cup cup = null;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileReader("cups.txt"));
            while (scanner.hasNextLine()) {
                String[] cupData = scanner.nextLine().split(",");
                int id = Integer.parseInt(cupData[0]);
                String color = cupData[1];
                String shape = cupData[2];
                double price = Double.parseDouble(cupData[3]);
                if (id == cupId) {
                    cup = new Cup(id, color, shape, BigDecimal.valueOf(price));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cup == null) {
            throw new RuntimeException("there is no Cup with this id");
        } else {
            return cup;
        }
    }
}
