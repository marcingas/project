package pl.marcin.project.controller;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.CupRepositoryListBased;
import pl.marcin.project.repository.CustomerRepositoryListBased;
import pl.marcin.project.repository.PurchaseRepositoryListBased;
import pl.marcin.project.service.CupService;
import pl.marcin.project.service.CustomerService;
import pl.marcin.project.service.PurchaseService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Shop {
    public static void main(String[] args) {

        PurchaseService purchaseService= new PurchaseService(new PurchaseRepositoryListBased());

        Customer jas = new Customer(1,"Jaś","Kowalski","Kraków");
        List<Cup> cupOrder = new ArrayList<>();
        cupOrder.add(new Cup(1,"blue","square", BigDecimal.valueOf(5)));
        cupOrder.add(new Cup(2,"Orange","circle", BigDecimal.valueOf(9)));
        cupOrder.add(new Cup(3,"yellow","square", BigDecimal.valueOf(6)));
        List<Cup> cupOrder2 = new ArrayList<>();
        cupOrder2.add(new Cup(4,"white","square", BigDecimal.valueOf(5)));
        cupOrder2.add(new Cup(5,"Orange","circle", BigDecimal.valueOf(9)));
        cupOrder2.add(new Cup(6,"yellow","square", BigDecimal.valueOf(6)));
        List<Cup> cupOrder3 = new ArrayList<>();
        cupOrder3.add(new Cup(7,"Orange","circle", BigDecimal.valueOf(9)));
        cupOrder3.add(new Cup(8,"yellow","square", BigDecimal.valueOf(6)));
        Purchase purchase = new Purchase(jas,cupOrder);
        Purchase purchase2 = new Purchase(jas,cupOrder2);
        Purchase purchase3 = new Purchase(jas,cupOrder3);


        System.out.println(purchaseService.savePurchase(purchase));
        System.out.println(purchaseService.savePurchase(purchase2));
        System.out.println(purchaseService.savePurchase(purchase3));
        System.out.println(purchaseService.purchaseHistoryByCustomerId(1));
        System.out.println(summaryOfTransactionsById(purchaseService,1));


    }
    public static String summaryOfTransactionsById(PurchaseService purchaseService, int id){
       return "Customer with id " + id + " made: "
               + purchaseService.purchaseHistoryByCustomerId(id).size() + " purchases";

    }
}
