package pl.marcin.project.database;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:test-data.sql")
class PurchaseEntityRepositoryTest {
    @Autowired
    PurchaseEntityRepository purchaseEntityRepository;
    @Autowired
    CustomerEntityRepository customerEntityRepository;
    @Autowired
    CupEntityRepository cupEntityRepository;


    @Test
    void findByCustomerCustomerId() {
        //given
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(BigDecimal.valueOf(12.23));
        CustomerEntity customer = customerEntityRepository.findById(1L).orElseThrow(() -> new RuntimeException("Not found"));
        purchaseEntity.setCustomer(customer);

        //when
        purchaseEntityRepository.save(purchaseEntity);
        List<PurchaseEntity> results = purchaseEntityRepository
                .findByCustomerCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Nothing found"));

        //then
        assertNotNull(results);
        assertEquals(purchaseEntity, results.get(0));
    }

    @Test
    void savePurchaseEntity() {
        //given
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(BigDecimal.valueOf(12.23));
        CustomerEntity customer = customerEntityRepository.findById(1L).orElseThrow(() -> new RuntimeException("Not found"));
        purchaseEntity.setCustomer(customer);
        List<CupEntity> cups = cupEntityRepository.findAll();
        purchaseEntity.setCups(cups);

        //when
        PurchaseEntity result = purchaseEntityRepository.save(purchaseEntity);

        //then
        assertNotNull(result);
        assertEquals(customer, result.getCustomer());
    }

    @Test
    void findCustomerByPurchaseId() {
        //given
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(BigDecimal.valueOf(12.23));
        CustomerEntity customer = customerEntityRepository.findById(1L).orElseThrow(() -> new RuntimeException("Not found"));
        purchaseEntity.setCustomer(customer);

        //when
        PurchaseEntity savedPurchase = purchaseEntityRepository.save(purchaseEntity);
        CustomerEntity resultCustomer = purchaseEntityRepository.
                findCustomerByPurchaseId(savedPurchase.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Not found"));

        //then
        assertNotNull(resultCustomer.getCustomerId());
        assertNotNull(resultCustomer);
        assertEquals(customer, resultCustomer);
    }
}