package pl.marcin.project.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PurchaseEntityRepositoryTest {
    @Autowired
    private PurchaseEntityRepository purchaseEntityRepository;
    @Autowired
    private CustomerEntityRepository customerEntityRepository;

    @Test
    void findByCustomerCustomerId() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntityRepository.saveAndFlush(customerEntity);
        purchaseEntityRepository.saveAndFlush(new PurchaseEntity(customerEntity,
                BigDecimal.valueOf(2.14), new ArrayList<>()));
        List<PurchaseEntity> purchaseEntities = purchaseEntityRepository.findByCustomerCustomerId(
                customerEntityRepository.findAll().get(0).getCustomerId()).get();
        assertNotNull(purchaseEntities);

    }


}