package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Cup;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CupRepositoryListBasedTest {
    private int notExistingCupId = 1;
    private int expectedCupId = 2;
    private int expectedCupId2 = 3;
    private Cup expectedCup = Cup.builder().id(expectedCupId).color("White").build();
    private Cup expectedCup2 = Cup.builder().id(expectedCupId2).color("Blue").build();
    private CupRepository cupRepository = new CupRepositoryListBased();

    @Test
    public void shouldReturnCupById() {

        //given

        cupRepository.saveCup(expectedCup);

        //when

        Cup searchedCup = cupRepository.findCup(expectedCupId);

        //then

        Assertions.assertEquals(expectedCupId, searchedCup.getId());
    }

    @Test
    public void shouldThrowExceptionIfNotFindCupById() {

        //when:

        cupRepository.saveCup(expectedCup);

        //then:

        assertThrows(RuntimeException.class, () -> {
            cupRepository.findCup(notExistingCupId);
        });
    }

    @Test
    public void shouldThrowExceptionMessageNoSuchCupById() {

        //given:

        cupRepository.saveCup(expectedCup);

        //when:

        var exception = assertThrows(RuntimeException.class, () -> {
            cupRepository.findCup(notExistingCupId);
        });

        //then:

        Assertions.assertEquals("There is no cup with this id", exception.getMessage());
    }

    @Test
    public void shouldReturnListOfAllCups() {

        //given

        cupRepository.saveCup(expectedCup);
        cupRepository.saveCup(expectedCup2);

        //when:

        List<Cup> list = cupRepository.findCups();

        //then:

        Assertions.assertEquals(List.of(expectedCup, expectedCup2), list);
    }

    @Test
    public void shouldReturnOneElementIfCupDeleted() {
        //given

        cupRepository.saveCup(expectedCup);
        cupRepository.saveCup(expectedCup2);

        //when

        boolean ifCupDeleted = cupRepository.deleteCup(expectedCupId);

        //then

        Assertions.assertEquals(List.of(expectedCup2), cupRepository.findCups());
    }

    @Test
    public void shouldReturnTrueIfCupDeleted() {

        //given

        cupRepository.saveCup(expectedCup);

        //when

        boolean ifCupDeleted = cupRepository.deleteCup(expectedCupId);

        //then

        Assertions.assertTrue(ifCupDeleted);
    }

    @Test
    public void shouldReturnFalseIfCupNotDeleted() {

        //given

        cupRepository.saveCup(expectedCup);

        //when

        boolean ifCupDeleted = cupRepository.deleteCup(notExistingCupId);

        //then

        Assertions.assertFalse(ifCupDeleted);
    }

    @Test
    public void shouldReturnCupIdWhenSaved() {

        //when

        int returnedId = cupRepository.saveCup(expectedCup);

        //then

        Assertions.assertEquals(expectedCupId, returnedId);
    }

    @Test
    public void shouldEqualToSavedCup() {

        //when

        cupRepository.saveCup(expectedCup);

        //then

        Assertions.assertEquals(expectedCup, cupRepository.findCup(expectedCupId));
    }

    @Test
    public void shouldEqualToUpdatedCup() {

        //given

        cupRepository.saveCup(expectedCup);
        Cup updatedCup = Cup.builder().id(expectedCupId).color("Blue").build();

        //when

        cupRepository.updateCup(expectedCupId, updatedCup);

        //then

        Assertions.assertEquals(updatedCup, cupRepository.findCup(expectedCupId));
    }

    @Test
    public void shouldThrowExceptionNoCup() {
        //given

        cupRepository.saveCup(expectedCup);

        //when

        Cup updatedCup = Cup.builder().id(3).shape("circle").build();

        //then
        assertThrows(RuntimeException.class, () -> {
            cupRepository.updateCup(3, updatedCup);
        });
    }

    @Test
    public void shouldReturnUpdatedCupId() {

        //given

        cupRepository.saveCup(expectedCup);

        //when

        Cup updatedCup = Cup.builder()
                .id(expectedCupId)
                .shape("square")
                .color("White")
                .build();

        int returnedId = cupRepository.updateCup(expectedCupId, updatedCup);
        //then
        Assertions.assertEquals(expectedCupId, returnedId);
    }


}