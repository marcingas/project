package pl.marcin.project.repository;

import java.util.logging.Logger;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import pl.marcin.project.model.Cup;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class CupRepositoryFileBased implements CupRepository {
    //private static Logger logger = Logger.getLogger(CupRepositoryFileBased.class.getName());
//    private static final File FILE = new File("cups.txt");

    @Override
    public int saveCup(Cup cup) {
        try {
            File file = new File("cups.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(cup.getId() + "," + cup.getColor() + "," + cup.getShape() + "," +
                    cup.getPrice() + "\n");
            fileWriter.close();

            // info
            // error
            // warn
            // debug
            // trace
            // fatal

            log.info("new cup saved with id {}", cup.getId());
        } catch (IOException e) {
            log.warn("cannot save cup with id {} because of: {}", cup.getId(), e.getMessage());
        }
        return cup.getId();
    }

    @Override
    public int updateCup(int cupId, Cup cup) {
        File file = new File("cups.txt");
        File tempFile = new File("cupstemp.txt");
        Scanner scanner = null;
        FileWriter tempFileWriter = null;
        try {
            scanner = new Scanner(new FileReader(file));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String[] cupData = scanner.nextLine().split(",");
                int id = Integer.parseInt(cupData[0]);
                String color = cupData[1];
                String shape = cupData[2];
                double price = Double.parseDouble(cupData[3]);

                tempFileWriter = new FileWriter(tempFile, true);
                if (id == cupId) {
                    tempFileWriter.write(cup.getId() + "," + cup.getColor() + "," + cup.getShape() + "," +
                            cup.getPrice() + "\n");
                } else {
                    tempFileWriter.write(id + "," + color + "," + shape + "," +
                            price + "\n");
                }

            }
            tempFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace(); // log...
        } finally {
            scanner.close();
        }
        if (file.delete()) {
            System.out.println("File removed");
        } else {
            System.out.println("File couldn't be removed");
        }
        if (tempFile.renameTo(file)) {
            System.out.println("ok");
        } else {
            System.out.println("not ok");
        }
        return cupId;
    }


    @Override
    public boolean deleteCup(int cupId) {
        File tempFile = new File("cupstemp1.txt");
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
                if (id != cupId) {
                    fileWriter.write(id + "," + color + "," + shape + "," +
                            price + "\n");
                }
                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            scanner.close();
        }
        return true;
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
