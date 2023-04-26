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

        buyCups(purchaseService,new Purchase(jas,cupOrder));
        buyCups(purchaseService,new Purchase(jas,cupOrder2));
        buyCups(purchaseService,new Purchase(jas,cupOrder3));

        System.out.println(summaryOfTransactionsById(purchaseService,1));


    }
    public static void buyCups(PurchaseService purchaseService,Purchase purchase){
        System.out.println("Cups order processing....\n" + purchaseService.savePurchase(purchase) + ", cups bought!");

    }
    public static String summaryOfTransactionsById(PurchaseService purchaseService, int id){
        System.out.println("\n======Summary of transaction:========");
        System.out.println("\n----1. Customer's purchase history:----\n" + purchaseService.purchaseHistoryByCustomerId(id));
       return "----2. number of Transactions: -----" + "\nCustomer with id " + id + " made: "
               + purchaseService.purchaseHistoryByCustomerId(id).size() + " purchases";

    }
}
