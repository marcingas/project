package pl.marcin.project.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.marcin.project.database.PurchaseEntityRepository;
import pl.marcin.project.entityService.PurchaseEntityService;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PurchaseEntityControllerTest {
    @Mock
    PurchaseEntityRepository purchaseEntityRepository;
    @InjectMocks
    PurchaseEntityService purchaseEntityService;

    @Test
    void addPurchase() {
    }

    @Test
    void getAllPurchases() {
    }

    @Test
    void getCustomerHistory() {
    }
}