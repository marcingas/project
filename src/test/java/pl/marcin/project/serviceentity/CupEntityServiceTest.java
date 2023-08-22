package pl.marcin.project.serviceentity;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.database.CupEntityRepository;
import pl.marcin.project.entity.CupEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CupEntityServiceTest {
    @Mock
    private CupEntityRepository cupEntityRepository;
    @InjectMocks
    private CupEntityService cupEntityService;

    @Test
    void getAllCups() {
        //given
        List<CupEntity> mockList = new ArrayList<>();
        mockList.add(new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14)));
        mockList.add(new CupEntity(2L, "black", "circle", BigDecimal.valueOf(2.15)));
        mockList.add(new CupEntity(3L, "red", "circle", BigDecimal.valueOf(2.16)));
        when(cupEntityRepository.findAll()).thenReturn(mockList);

        //when
        List<CupEntity> result = cupEntityService.getAllCups();

        //then
        assertEquals(mockList.size(), result.size());
        verify(cupEntityRepository, times(1)).findAll();
    }

    @Test
    void getCupById() {
        //given
        CupEntity cupEntity = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        when(cupEntityRepository.findById(cupEntity.getCupId())).thenReturn(Optional.of(cupEntity));

        //when
        CupEntity result = cupEntityService.getCupById(cupEntity.getCupId());

        //then
        assertEquals(cupEntity, result);
        verify(cupEntityRepository, times(1)).findById(cupEntity.getCupId());
    }

    @Test
    void getCupByIdThrowsException() {
        //given
        CupEntity cupEntity = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        Long notExistingId = 6L;
        when(cupEntityRepository.findById(notExistingId)).thenReturn(Optional.empty());

        //when then
        assertThrows(EntityNotFoundException.class, () -> cupEntityService.getCupById(notExistingId));
    }

    @Test
    void addCup() {
        //given
        CupEntity cupEntity = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        when(cupEntityRepository.save(cupEntity)).thenReturn(cupEntity);
        //when
        CupEntity result = cupEntityService.addCup(cupEntity);

        //then
        assertEquals(cupEntity, result);
    }

    @Test
    void updateCup() {
        //given
        CupEntity existingCup = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        CupEntity updatedCup = new CupEntity(1L, "Red", "circle", BigDecimal.valueOf(2.45));
        when(cupEntityRepository.findById(existingCup.getCupId())).thenReturn(Optional.of(existingCup));
        when(cupEntityRepository.save(any(CupEntity.class))).thenReturn(updatedCup);

        //when
        CupEntity result = cupEntityService.updateCup(updatedCup);

        //then
        assertEquals(updatedCup, result);
        verify(cupEntityRepository).findById(existingCup.getCupId());
        verify(cupEntityRepository).save(updatedCup);
    }

    @Test
    void deleteCup() {
        //given
        CupEntity existingCup = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        when(cupEntityRepository.findById(existingCup.getCupId())).thenReturn(Optional.of(existingCup));
        //when
        cupEntityService.deleteCup(existingCup.getCupId());
        verify(cupEntityRepository).findById(existingCup.getCupId());
        verify(cupEntityRepository).delete(existingCup);
    }
}