package pl.marcin.project.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.marcin.project.model.Cup;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CupRepositoryListBasedTest {
    private int notExistingCupId = 1;
    private int expectedCupId = 2;
    private int expectedCupId2 = 3;
    private Cup expectedCup = Cup.builder().cupId(expectedCupId).color("White").build();
    private Cup expectedCup2 = Cup.builder().cupId(expectedCupId2).color("Blue").build();
    private CupRepository cupRepository = new CupRepositoryListBased();

    @Test
    public void shouldReturnCupById() {
        //given
        cupRepository.saveCup(expectedCup);

        //when
        Cup searchedCup = cupRepository.findCup(expectedCupId);

        //then
        Assertions.assertEquals(expectedCupId, searchedCup.getCupId());
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
        cupRepository.deleteCup(expectedCupId);

        //then
        Assertions.assertEquals(List.of(expectedCup2), cupRepository.findCups());
    }

    @Test
    public void shouldReturnTrueIfCupDeletedOrFalseIfNot() {

        //given
        cupRepository.saveCup(expectedCup);

        //when
        boolean ifCupDeleted = cupRepository.deleteCup(expectedCupId);
        boolean ifCupNotDeleted = cupRepository.deleteCup(notExistingCupId);

        //then
        Assertions.assertTrue(ifCupDeleted);
        Assertions.assertFalse(ifCupNotDeleted);
    }

    @Test
    public void shouldReturnCupIdWhenSaved() {

        //given,when
        int returnedId = cupRepository.saveCup(expectedCup);

        //then
        Assertions.assertEquals(expectedCupId, returnedId);
    }

    @Test
    public void shouldEqualToUpdatedCup() {

        //given
        cupRepository.saveCup(expectedCup);
        Cup updatedCup = Cup.builder().cupId(expectedCupId).color("Blue").build();

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
        Cup updatedCup = Cup.builder().cupId(3).shape("circle").build();

        //then
        assertThrows(RuntimeException.class, () -> {
            cupRepository.updateCup(3, updatedCup);
        });
    }
}