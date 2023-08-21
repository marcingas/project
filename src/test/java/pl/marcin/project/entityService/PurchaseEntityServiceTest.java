package pl.marcin.project.entityService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseEntityServiceTest {
    @Mock
    PurchaseEntityRepository purchaseEntityRepository;
    @InjectMocks
    PurchaseEntityService purchaseEntityService;

    @Test
    void getAllPurchases() {
        List<PurchaseEntity> mockList = new ArrayList<>();
        List<CupEntity> cupList = new ArrayList<>();
        mockList.add(new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), cupList));
        mockList.add(new PurchaseEntity(2L, new CustomerEntity(),
                BigDecimal.valueOf(2.14), cupList));
        mockList.add(new PurchaseEntity(3L, new CustomerEntity(),
                BigDecimal.valueOf(2.15), cupList));
        when(purchaseEntityRepository.findAll()).thenReturn(mockList);
        List<PurchaseEntity> result = purchaseEntityService.getAllPurchases();
        assertEquals(mockList.size(), result.size());
        verify(purchaseEntityRepository, times(1)).findAll();
    }

    @Test
    void getPurchase() {
        List<CupEntity> cupList = new ArrayList<>();
        PurchaseEntity purchaseEntity = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), cupList);
        when(purchaseEntityRepository.findById(purchaseEntity.getPurchaseId())).thenReturn(Optional.of(purchaseEntity));
        verify(purchaseEntityRepository, times(1)).findById(purchaseEntity.getPurchaseId());
    }

    @Test
    void getPurchaseException() {
        List<CupEntity> cupList = new ArrayList<>();
        PurchaseEntity purchaseEntity = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), cupList);
        Long notExistingId = 7L;
        when(purchaseEntityRepository.findById(notExistingId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> purchaseEntityService.getPurchase(notExistingId));

    }

    @Test
    void getPurchaseHistoryByCustomerId() {
    }

    @Test
    void addPurchase() {
    }

    @Test
    void updatePurchase() {
    }

    @Test
    void deletePurchase() {
    }
}