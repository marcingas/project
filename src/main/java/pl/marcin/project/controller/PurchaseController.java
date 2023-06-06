package pl.marcin.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.entityService.PurchaseEntityService;
import pl.marcin.project.request.PurchaseRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseEntityService purchaseEntityService;
    private final CustomerEntityService customerEntityService;

    @GetMapping("/purchases")
    public List<PurchaseEntity> getPurchases() {
        return purchaseEntityService.getAllPurchases();
    }

    @GetMapping("/purchases/{id}")
    public PurchaseEntity getPurchase(@PathVariable Long id) {
        return purchaseEntityService.getPurchase(id);
    }

    @PostMapping("customers/{id}/purchase")
    public ResponseEntity<PurchaseEntity> addPurchaseToCustomerById(@PathVariable Long id,
                                                                    @Valid @RequestBody PurchaseRequest purchaseRequest) {

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setCustomer(customerEntityService.getCustomer(id));
        purchaseEntity.setPurchaseCost(purchaseRequest.getCost());
        purchaseEntity.setCups(purchaseRequest.getCups());

        purchaseEntityService.addPurchase(purchaseEntity);
        return new ResponseEntity<PurchaseEntity>(purchaseEntity, HttpStatus.CREATED);
    }

    @PutMapping("purchases/{id}")
    public ResponseEntity<PurchaseEntity> updatePurchases(
            @PathVariable Long id, @Valid @RequestBody PurchaseEntity purchaseEntity) {
        purchaseEntity.setPurchaseId(id);
        purchaseEntityService.updatePurchase(purchaseEntity);
        return new ResponseEntity<PurchaseEntity>(purchaseEntity, HttpStatus.OK);
    }

    @DeleteMapping("/purchases")
    public ResponseEntity<HttpStatus> deletePurchase(@RequestParam Long id) {
        purchaseEntityService.deletePurchase(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
