package pl.marcin.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.marcin.project.entityService.PurchaseEntityReactiveService;
import pl.marcin.project.model.Purchase;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/purchases")
public class PurchaseControllerReactive {
    @Autowired
    private PurchaseEntityReactiveService purchaseEntityReactiveService;

    @GetMapping
    private Flux<Purchase> getPurchases() {
        return purchaseEntityReactiveService.getAllPurchases();
    }
}
