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

        PurchaseService purchaseService = new PurchaseService(new PurchaseRepositoryListBased());
        CustomerService customerService = new CustomerService(new CustomerRepositoryListBased());
        CupService cupService = new CupService(new CupRepositoryListBased());

        Cup cup1 = new Cup(1, "Blue", "square", BigDecimal.valueOf(4.22));
        Cup cup2 = new Cup(2, "Yellow", "circle", BigDecimal.valueOf(5.22));
        Cup cup3 = new Cup(3, "Orange", "long", BigDecimal.valueOf(6.22));
        Cup cup4 = new Cup(4, "White", "thin", BigDecimal.valueOf(2.22));
        Cup cup5 = new Cup(5, "Orange", "Square", BigDecimal.valueOf(3.22));
        Cup cup6 = new Cup(6, "Khaki", "square", BigDecimal.valueOf(4.88));

        addToStock(cupService, cup1);
        addToStock(cupService, cup2);
        addToStock(cupService, cup3);
        addToStock(cupService, cup4);
        addToStock(cupService, cup5);
        addToStock(cupService, cup6);

        System.out.println(viewStock(cupService));


        Customer jas = new Customer(1, "Jaś", "Kowalski", "Kraków");
        List<Cup> cupOrder = new ArrayList<>();
        cupOrder.add(cup1);
        cupOrder.add(cup2);
        cupOrder.add(cup3);
        List<Cup> cupOrder2 = new ArrayList<>();
        cupOrder2.add(cup4);
        cupOrder2.add(cup5);

        List<Cup> cupOrder3 = new ArrayList<>();
        cupOrder3.add(cup6);

        buyCups(purchaseService,customerService,cupService,new Purchase(jas,cupOrder),jas);
        buyCups(purchaseService,customerService,cupService,new Purchase(jas,cupOrder2),jas);
        buyCups(purchaseService,customerService,cupService,new Purchase(jas,cupOrder3),jas);
        summaryOfCustomerTransactions(purchaseService, customerService, jas);


    }

    public static void buyCups(PurchaseService purchaseService, CustomerService customerService,
                               CupService cupService, Purchase purchase, Customer customer) {
        for (Cup cup : purchase.getCups()) {
            cupService.sellCup(cup);
        }
        purchaseService.savePurchase(purchase);
        customer.updatePurchaseHistory(purchase);

    }

    public static void addToStock(CupService cupService, Cup cup) {
        cupService.addCup(cup);
    }

    public static List<Cup> viewStock(CupService cupService) {
        return cupService.showAllCups();

    }

    public static String summaryOfCustomerTransactions(PurchaseService purchaseService,
                                                       CustomerService customerService, Customer customer) {
        System.out.println("=======Customer profile: ===========");
        System.out.println(customer);
        System.out.println("\n======Summary of transactions:========");
        System.out.println("\n----1. Customer's purchase history:----\n"
                + customer.getPurchaseHistory());
        return "----2. number of Transactions: -----" + "\nCustomer with id " + customer.getId() + " made: "
                + customer.getPurchaseHistory().size() + " purchases";

    }
}
