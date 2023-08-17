package pl.marcin.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.entity.CustomerEntity;
import pl.marcin.project.entity.PurchaseEntity;
import pl.marcin.project.entityService.CupEntityService;
import pl.marcin.project.entityService.CustomerEntityService;
import pl.marcin.project.entityService.PurchaseEntityService;
import pl.marcin.project.request.PurchaseRequest;

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
    public PurchaseEntity addPurchase(@RequestBody PurchaseRequest purchase) {
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setPurchaseCost(purchase.getCost());

        List<CupEntity> cups = new ArrayList<>();

        for (var purchaseID : purchase.getCupIds()) {
            cups.add(cupEntityService.getCupById(purchaseID));
        }

        purchaseEntity.setCups(cups);
        CustomerEntity customer = customerEntityService.getCustomer(purchase.getCustomerId());
        purchaseEntity.setCustomer(customer);
        return purchaseEntityService.addPurchase(purchaseEntity);
    }

    @GetMapping
    public List<PurchaseEntity> getAllPurchases() {
        return purchaseEntityService.getAllPurchases();
    }

    @GetMapping("/{customerId}")
    public List<PurchaseEntity> getCustomerHistory(@PathVariable Long customerId) {
        return purchaseEntityService.getPurchaseHistoryByCustomerId(customerId);
    }
}
