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

        Customer jas = new Customer(1, "Jaś", "Kowalski", "Kraków");
        createCustomerAccount(customerService, jas);
        Customer stas = new Customer(2, "Staszek", "Buła", "Żywiec");
        createCustomerAccount(customerService, stas);

        Cup cup1 = new Cup(1, "Blue", "square", BigDecimal.valueOf(4.22));
        Cup cup2 = new Cup(2, "Yellow", "circle", BigDecimal.valueOf(5.22));
        Cup cup3 = new Cup(3, "Orange", "long", BigDecimal.valueOf(6.22));
        Cup cup4 = new Cup(4, "White", "thin", BigDecimal.valueOf(2.22));
        Cup cup5 = new Cup(5, "Orange", "Square", BigDecimal.valueOf(3.22));
        Cup cup6 = new Cup(6, "Khaki", "square", BigDecimal.valueOf(4.88));
        Cup cup7 = new Cup(7, "Orange", "long", BigDecimal.valueOf(3.14));
        Cup cup8 = new Cup(8, "Orange", "long and circle", BigDecimal.valueOf(31.14));

        addToStock(cupService, cup1);
        addToStock(cupService, cup2);
        addToStock(cupService, cup3);
        addToStock(cupService, cup4);
        addToStock(cupService, cup5);
        addToStock(cupService, cup6);
        addToStock(cupService, cup7);
        addToStock(cupService, cup8);

        System.out.println(viewStock(cupService));

        List<Cup> cupBasket = new ArrayList<>();
        System.out.println(addCupToBasket(cupService, cupBasket, cup1));
        System.out.println(addCupToBasket(cupService, cupBasket, cup1));

        List<Cup> cupBasket2 = new ArrayList<>();
        System.out.println(addCupToBasket(cupService, cupBasket2, cup4));
        System.out.println(addCupToBasket(cupService, cupBasket2, cup5));


        List<Cup> cupBasket3 = new ArrayList<>();
        System.out.println(addCupToBasket(cupService, cupBasket3, cup6));
        List<Cup> cupBasket4 = new ArrayList<>();
        System.out.println(addCupToBasket(cupService, cupBasket4, cup8));

        List<Cup> cupBasket5 = new ArrayList<>();
        System.out.println(addCupToBasket(cupService, cupBasket5, cup7));
        System.out.println(addCupToBasket(cupService, cupBasket5, cup6));
        System.out.println(addCupToBasket(cupService, cupBasket5, cup6));
        System.out.println(addCupToBasket(cupService, cupBasket5, cup6));


        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket), jas);
        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket2), jas);
        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket3), jas);
        buyCups(purchaseService, customerService, cupService, new Purchase(stas, cupBasket4), stas);
        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket5), jas);
        System.out.println(summaryOfCustomerTransactions(purchaseService, customerService, jas));
        System.out.println(viewStock(cupService));
        System.out.println(purchaseHistory(purchaseService));


    }

    public static void createCustomerAccount(CustomerService customerService, Customer customer) {
        customerService.addCustomer(customer);
        System.out.println(customer.getName() + " Your account has been created. Now You can order Cups!");
    }

    public static String addCupToBasket(CupService cupService, List<Cup> orderList, Cup cup) {
        System.out.println("====add to Basket processing....====");
        Cup temporaryCup = null;
        for (Cup cupOnStock : cupService.showAllCups()) {
            if (cup.getId() == cupOnStock.getId()) {
                temporaryCup = cup;
            }
        }
        if(temporaryCup==null){
            return "Cup currently unavailable";
        }else{
            cupService.sellCup(temporaryCup);
            orderList.add(temporaryCup);
            return "Cup successfully added to orderList";
        }
    }

    public static void buyCups(PurchaseService purchaseService, CustomerService customerService,
                               CupService cupService, Purchase purchase, Customer customer) {
        purchaseService.savePurchase(purchase);
        customerService.updatePurchaseHistory(purchase, customer);
        System.out.println("====Customer " + customer.getName() + "'s order confirmation=====\n" + purchase);

    }

    public static void addToStock(CupService cupService, Cup cup) {
        System.out.println("====Adding Cup to stock======== ");
        cupService.addCup(cup);
        System.out.println(cup + " Added to Stock, available for sale");
    }

    public static List<Cup> viewStock(CupService cupService) {
        System.out.println("==========Actual stock availability: ===========");
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
    public static List<Purchase> purchaseHistory(PurchaseService purchaseService){
        System.out.println("====All Purchases (history)======\n");
       return purchaseService.AllPurchasesHistory();
    }
}
