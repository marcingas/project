package pl.marcin.project.entityService;

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
        List<CupEntity> mockList = new ArrayList<>();
        mockList.add(new CupEntity(1L, "white", "circle", BigDecimal.valueOf(2.14)));
        mockList.add(new CupEntity(2L, "black", "circle", BigDecimal.valueOf(2.15)));
        mockList.add(new CupEntity(3L, "red", "circle", BigDecimal.valueOf(2.16)));
        when(cupEntityRepository.findAll()).thenReturn(mockList);

        List<CupEntity> result = cupEntityService.getAllCups();
        assertEquals(mockList.size(), result.size());
        verify(cupEntityRepository, times(1)).findAll();
    }

    @Test
    void getCupById() {
        CupEntity cupEntity = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        when(cupEntityRepository.findById(cupEntity.getCupId())).thenReturn(Optional.of(cupEntity));
        CupEntity result = cupEntityService.getCupById(cupEntity.getCupId());
        assertEquals(cupEntity, result);
        verify(cupEntityRepository, times(1)).findById(cupEntity.getCupId());
    }

    @Test
    void getCupByIdThrowsException() {
        CupEntity cupEntity = new CupEntity(1L, "White", "circle", BigDecimal.valueOf(2.14));
        Long notExistingId = 6L;
        when(cupEntityRepository.findById(notExistingId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> cupEntityService.getCupById(notExistingId));
    }

    @Test
    void addCup() {
    }

    @Test
    void updateCup() {
    }

    @Test
    void deleteCup() {
    }
}