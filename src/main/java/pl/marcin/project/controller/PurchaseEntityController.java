package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.request.PurchaseRequest;
import pl.marcin.project.serviceentity.CupEntityService;
import pl.marcin.project.serviceentity.CustomerEntityService;
import pl.marcin.project.serviceentity.PurchaseEntityService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseEntityController {
    @Autowired
    private final PurchaseEntityService purchaseEntityService;
    @Autowired
    private final CustomerEntityService customerEntityService;
    @Autowired
    private final CupEntityService cupEntityService;


    @PostMapping("/add")
    public PurchaseEntity addPurchase(@RequestBody PurchaseRequest purchaseRequest) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(purchaseRequest.getCost());

        List<CupEntity> cups = new ArrayList<>();
        List<Long> cupIds = purchaseRequest.getCupIds();
        if (cupIds != null) {
            for (var purchaseID : cupIds) {
                cups.add(cupEntityService.getCupById(purchaseID));
            }
        }
        purchaseEntity.setCups(cups);
        CustomerEntity customer = customerEntityService.getCustomer(purchaseRequest.getCustomerId());
        purchaseEntity.setCustomer(customer);
        return purchaseEntityService.addPurchase(purchaseEntity);
    }

    @PutMapping("/{purchaseId}/update")
    public PurchaseEntity updatePurchase(@PathVariable Long purchaseId, @RequestBody PurchaseRequest purchaseRequest) {

        CustomerEntity customerEntity = customerEntityService.getCustomer(purchaseRequest.getCustomerId());
        PurchaseEntity purchaseEntity = purchaseEntityService.getPurchase(purchaseId);
        purchaseEntity.setPurchaseCost(purchaseRequest.getCost());
        purchaseEntity.setCustomer(customerEntity);
        List<CupEntity> cups = new ArrayList<>();
        List<Long> cupIds = purchaseRequest.getCupIds();
        if (cupIds != null) {
            for (var cupId : purchaseRequest.getCupIds()) {
                cups.add(cupEntityService.getCupById(cupId));
            }
        }
        purchaseEntity.setCups(cups);
        return purchaseEntityService.updatePurchase(purchaseEntity);
    }

    @GetMapping("/{purchaseId}")
    public PurchaseEntity getPurchase(@PathVariable Long purchaseId) {
        return purchaseEntityService.getPurchase(purchaseId);
    }

    @GetMapping
    public List<PurchaseEntity> getAllPurchases() {
        return purchaseEntityService.getAllPurchases();
    }

    @GetMapping("/history/{customerId}")
    public List<PurchaseEntity> getCustomerHistory(@PathVariable Long customerId) {
        return purchaseEntityService.getPurchaseHistoryByCustomerId(customerId);
    }

    @DeleteMapping("/{purchaseId}/delete")
    public Long deletePurchase(@PathVariable Long purchaseId) {
        purchaseEntityService.deletePurchase(purchaseId);
        return purchaseId;
    }
}
