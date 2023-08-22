package pl.marcin.project.serviceentity;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseEntityServiceTest {
    @Mock
    PurchaseEntityRepository purchaseEntityRepository;
    @InjectMocks
    PurchaseEntityService purchaseEntityService;

    @Test
    void getAllPurchases() {
        //given
        List<PurchaseEntity> mockList = new ArrayList<>();
        List<CupEntity> cupList = new ArrayList<>();
        mockList.add(new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), cupList));
        mockList.add(new PurchaseEntity(2L, new CustomerEntity(),
                BigDecimal.valueOf(2.14), cupList));
        mockList.add(new PurchaseEntity(3L, new CustomerEntity(),
                BigDecimal.valueOf(2.15), cupList));
        when(purchaseEntityRepository.findAll()).thenReturn(mockList);

        //when
        List<PurchaseEntity> result = purchaseEntityService.getAllPurchases();

        //then
        assertEquals(mockList.size(), result.size());
        verify(purchaseEntityRepository, times(1)).findAll();
    }

    @Test
    void getPurchase() {
        //given
        List<CupEntity> cupList = new ArrayList<>();
        PurchaseEntity purchaseEntity = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), cupList);
        when(purchaseEntityRepository.findById(purchaseEntity.getPurchaseId())).thenReturn(Optional.of(purchaseEntity));
        //when
        PurchaseEntity result = purchaseEntityService.getPurchase(purchaseEntity.getPurchaseId());
        assertEquals(purchaseEntity, result);
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
        //given
        List<PurchaseEntity> mockList = new ArrayList<>();
        List<CupEntity> cupList = new ArrayList<>();
        CustomerEntity existingCustomer = new CustomerEntity(1L, "John", "Cosby",
                new AddressEntity());
        mockList.add(new PurchaseEntity(1L, existingCustomer, BigDecimal.valueOf(2.13), cupList));
        mockList.add(new PurchaseEntity(2L, existingCustomer, BigDecimal.valueOf(2.14), cupList));
        mockList.add(new PurchaseEntity(3L, existingCustomer, BigDecimal.valueOf(2.15), cupList));
        when(purchaseEntityRepository.findByCustomerCustomerId(existingCustomer.getCustomerId()))
                .thenReturn(Optional.of(mockList));

        //when
        List<PurchaseEntity> result = purchaseEntityService.
                getPurchaseHistoryByCustomerId(existingCustomer.getCustomerId());

        //then
        assertEquals(mockList, result);
        verify(purchaseEntityRepository).findByCustomerCustomerId(existingCustomer.getCustomerId());
    }

    @Test
    void addPurchase() {
        //given
        List<CupEntity> cupList = new ArrayList<>();
        PurchaseEntity purchaseEntity = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), cupList);
        when(purchaseEntityRepository.save(purchaseEntity)).thenReturn(purchaseEntity);

        //when
        PurchaseEntity result = purchaseEntityService.addPurchase(purchaseEntity);

        //then
        assertEquals(purchaseEntity, result);
        verify(purchaseEntityRepository).save(purchaseEntity);
    }

    @Test
    void updatePurchase() {
        //given
        List<CupEntity> existingCupList = new ArrayList<>();
        List<CupEntity> updatedCupList = new ArrayList<>();
        PurchaseEntity existingPurchase = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), existingCupList);
        PurchaseEntity updatedPurchase = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.30), updatedCupList);
        when(purchaseEntityRepository.findById(existingPurchase.getPurchaseId())).
                thenReturn(Optional.of(existingPurchase));
        when(purchaseEntityRepository.save(updatedPurchase)).thenReturn(updatedPurchase);

        //when
        PurchaseEntity result = purchaseEntityService.updatePurchase(updatedPurchase);

        //then
        assertEquals(updatedPurchase, result);
        verify(purchaseEntityRepository).findById(existingPurchase.getPurchaseId());
        verify(purchaseEntityRepository).save(updatedPurchase);
    }

    @Test
    void deletePurchase() {
        //given
        PurchaseEntity existingPurchase = new PurchaseEntity(1L, new CustomerEntity(),
                BigDecimal.valueOf(2.13), new ArrayList<CupEntity>());
        when(purchaseEntityRepository.findById(existingPurchase.getPurchaseId())).
                thenReturn(Optional.of(existingPurchase));

        //when
        purchaseEntityService.deletePurchase(existingPurchase.getPurchaseId());

        //then
        verify(purchaseEntityRepository).findById(existingPurchase.getPurchaseId());
        verify(purchaseEntityRepository).delete(existingPurchase);
    }

    @Test
    void getCustomerByPurchaseId() {
        //given
        List<CupEntity> cupList = new ArrayList<>();
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(1L);
        customer.setName("John");
        customer.setSurname("Dowe");
        PurchaseEntity purchaseEntity = new PurchaseEntity(1L, customer,
                BigDecimal.valueOf(2.13), cupList);
        when(purchaseEntityRepository.save(any(PurchaseEntity.class))).thenReturn(purchaseEntity);
        when(purchaseEntityRepository.findCustomerByPurchaseId(purchaseEntity.getPurchaseId())).
                thenReturn(Optional.of(customer));

        //when
        PurchaseEntity savedPurchase = purchaseEntityRepository.save(purchaseEntity);
        CustomerEntity resultCustomer = purchaseEntityService.getCustomerByPurchaseId(savedPurchase.getPurchaseId());

        //then
        assertNotNull(resultCustomer);
        assertEquals(customer, resultCustomer);
        verify(purchaseEntityRepository, times(1)).save(purchaseEntity);
        verify(purchaseEntityRepository, times(1)).
                findCustomerByPurchaseId(savedPurchase.getPurchaseId());
    }
}