package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CupRepositoryListBasedTest {
    private CupRepository cupRepository = new CupRepositoryListBased();

    @Test
    public void shouldReturnCupById() {
        //given
        int id = 1;
        Cup expectedCup = Cup.builder().id(id).build();
        cupRepository.saveCup(expectedCup);

        //when
        Cup cup = cupRepository.findCup(id);

        //then
        Assertions.assertEquals(id, cup.getId());
    }

    @Test
    public void shouldReturnCupWithId2() {
        //given
        int id = 1;
        int id2 = 2;
        Cup expectedCup1 = Cup.builder().id(id).build();
        Cup expectedCup2 = Cup.builder().id(id2).build();
        cupRepository.saveCup(expectedCup1);
        cupRepository.saveCup(expectedCup2);

        //when
        Cup cup = cupRepository.findCup(id2);

        //then
        Assertions.assertEquals(id2, cup.getId());
    }

    @Test
    public void shouldThrowExceptionIfNotFindCupById() {
        //given
        int id = 1;
        int id2 = 2;
        //when:
        Cup cup2 = Cup.builder().id(id2).build();
        cupRepository.saveCup(cup2);

        //then:
        assertThrows(RuntimeException.class, () -> {
            cupRepository.findCup(id);
        });
    }

    @Test
    public void shouldThrowExceptionMessageNoSuchCupById() {
        //given
        int id = 1;
        int id2 = 2;
        Cup expectedCup2 = Cup.builder().id(id2).build();
        cupRepository.saveCup(expectedCup2);
        //when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            cupRepository.findCup(id);
        });

        //then
        Assertions.assertEquals("There is no cup with this id", exception.getMessage());
    }

    @Test
    public void shouldReturnListOfAllCups() {
        //given
        int id = 1;
        int id2 = 2;
        Cup cup1 = Cup.builder().id(id).build();
        Cup cup2 = Cup.builder().id(id2).build();
        cupRepository.saveCup(cup1);
        cupRepository.saveCup(cup2);
        //when
        List<Cup> list = cupRepository.findCups();
        //then
        Assertions.assertEquals(List.of(cup1, cup2), list);
    }

    @Test
    public void shouldReturnOneElementIfCupDeleted() {
        //given
        int id = 1;
        int id2 = 2;
        Cup cup = Cup.builder().id(id).color("orange").build();
        Cup cup2 = Cup.builder().id(id2).color("white").build();
        cupRepository.saveCup(cup);
        cupRepository.saveCup(cup2);
        //when
        boolean ifCupDeleted = cupRepository.deleteCup(id);
        //then
        Assertions.assertEquals(List.of(cup2), cupRepository.findCups());
    }

    @Test
    public void shouldReturnTrueIfCupDeleted() {
        //given
        int id = 1;
        Cup cup = Cup.builder().id(id).color("orange").build();
        cupRepository.saveCup(cup);
        //when
        boolean ifCupDeleted = cupRepository.deleteCup(id);
        //then
        Assertions.assertTrue(ifCupDeleted);
    }

    @Test
    public void shouldReturnFalseIfCupNotDeleted() {
        //given
        int id = 1;
        int id2 = 2;
        Cup cup = Cup.builder().id(id).color("orange").build();
        cupRepository.saveCup(cup);

        //when
        boolean ifCupDeleted = cupRepository.deleteCup(id2);
        //then
        Assertions.assertFalse(ifCupDeleted);
    }

    @Test
    public void shouldReturnCupIdWhenSaved() {
        //given
        int id = 1;
        Cup cup = Cup.builder()
                .id(id)
                .color("white")
                .shape("circle")
                .price(BigDecimal.valueOf(12.23))
                .build();
        //when
        int returnedId = cupRepository.saveCup(cup);
        //then
        Assertions.assertEquals(id, returnedId);
    }

    @Test
    public void shouldEqualToSavedCup() {
        //given
        int id = 1;
        Cup cup = Cup.builder()
                .id(id)
                .color("Orange")
                .shape("circle")
                .price(BigDecimal.valueOf(12.34))
                .build();
        //when
        cupRepository.saveCup(cup);
        //then
        Assertions.assertEquals(cup, cupRepository.findCup(id));
    }

    @Test
    public void shouldEqualToUpdatedCup() {
        //given
        int id = 1;
        Cup cup = Cup.builder()
                .id(id)
                .shape("circle")
                .color("Red")
                .build();
        cupRepository.saveCup(cup);

        //when
        Cup updatedCup = Cup.builder()
                .id(id)
                .shape("circle")
                .color("White")
                .price(BigDecimal.valueOf(12.21))
                .build();
        cupRepository.updateCup(id, updatedCup);
        //then
        Assertions.assertEquals(updatedCup, cupRepository.findCup(id));
    }

    @Test
    public void shouldThrowExceptionNoCup() {
        //given
        int id = 1;
        Cup cup = Cup.builder().id(id).build();
        cupRepository.saveCup(cup);

        //when
        Cup updatedCup = Cup.builder()
                .id(2)
                .shape("circle")
                .build();

        //then
        assertThrows(RuntimeException.class, () -> {
            cupRepository.updateCup(2, updatedCup);
        });
    }

    @Test
    public void shouldReturnUpdatedCupId() {
        //given
        int id = 1;
        Cup cup = Cup.builder().id(id).shape("circle").build();
        cupRepository.saveCup(cup);

        //when
        Cup updatedCup = Cup.builder()
                .id(id)
                .shape("square")
                .color("White")
                .build();

        int returnedId = cupRepository.updateCup(id, updatedCup);
        //then
        Assertions.assertEquals(id, returnedId);
    }


}