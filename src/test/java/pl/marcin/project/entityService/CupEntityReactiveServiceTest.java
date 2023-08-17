package pl.marcin.project.entityService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import pl.marcin.project.database.CupEntityReactiveRepository;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.model.Cup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;


@DataR2dbcTest
class CupEntityReactiveServiceTest {
    @Autowired
    CupEntityReactiveRepository cupEntityReactiveRepository;


    @Test
    void getAllCups() {

    }

    @Test
    void getAllCupsById() {
    }

    @Test
    void getCupsByPurchaseId() {
    }

    @Test
    void getCupById() {
    }

    @Test
    void saveCup() {


    }

    @Test
    void updateCup() {
    }

    @Test
    void deleteCup() {
        Assertions.assertTrue(true);
    }
}