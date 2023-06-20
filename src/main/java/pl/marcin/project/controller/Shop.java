package pl.marcin.project.controller;

import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
import pl.marcin.project.repository.CupRepository;
import pl.marcin.project.repository.CupRepositoryFileBased;
import pl.marcin.project.repository.CupRepositoryListBased;
import pl.marcin.project.repository.CustomerRepositoryListBased;
import pl.marcin.project.repository.PurchaseRepositoryListBased;
import pl.marcin.project.service.CupService;
import pl.marcin.project.service.CustomerService;
import pl.marcin.project.service.PurchaseService;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Shop {
    public static final File file = new File("cups.txt");

    public static void main(String[] args) {

    }

    public static void shopRunner() {
        file.delete();
        PurchaseService purchaseService = new PurchaseService(new PurchaseRepositoryListBased());
        CustomerService customerService = new CustomerService(new CustomerRepositoryListBased());
        CupService cupService = new CupService(new CupRepositoryListBased());

        Customer jas = new Customer(1, "Jaś", "Kowalski", "Kraków");
        addCustomer(customerService, jas);
        Customer stas = new Customer(2, "Staszek", "Buła", "Żywiec");
        addCustomer(customerService, stas);

        Cup cup1 = new Cup(1, "Blue", "square", BigDecimal.valueOf(1.2));
        Cup cup2 = new Cup(2, "Yellow", "circle", BigDecimal.valueOf(3.2));
        Cup cup3 = new Cup(3, "Orange", "long", BigDecimal.valueOf(2.33));
        Cup cup4 = new Cup(4, "White", "thin", BigDecimal.valueOf(2.23));
        Cup cup5 = new Cup(5, "Orange", "Square", BigDecimal.valueOf(32.12));
        Cup cup6 = new Cup(6, "Khaki", "square", BigDecimal.valueOf(2.1));
        Cup cup7 = new Cup(7, "Orange", "long", BigDecimal.valueOf(1.2));
        Cup cup8 = new Cup(8, "Orange", "long and circle", BigDecimal.valueOf(1.2));

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
        addCupToBasket(cupService, cupBasket, cup1);
        try {
            addCupToBasket(cupService, cupBasket, cup1);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        List<Cup> cupBasket2 = new ArrayList<>();
        addCupToBasket(cupService, cupBasket2, cup4);
        addCupToBasket(cupService, cupBasket2, cup5);


        List<Cup> cupBasket3 = new ArrayList<>();
        addCupToBasket(cupService, cupBasket3, cup6);
        List<Cup> cupBasket4 = new ArrayList<>();
        addCupToBasket(cupService, cupBasket4, cup8);

        List<Cup> cupBasket5 = new ArrayList<>();
        addCupToBasket(cupService, cupBasket5, cup7);
        try {
            addCupToBasket(cupService, cupBasket5, cup6);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        try {
            addCupToBasket(cupService, cupBasket5, cup6);
            addCupToBasket(cupService, cupBasket5, cup6);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }


        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket), jas);
        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket2), jas);
        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket3), jas);
        buyCups(purchaseService, customerService, cupService, new Purchase(stas, cupBasket4), stas);
        buyCups(purchaseService, customerService, cupService, new Purchase(jas, cupBasket5), jas);
        System.out.println(summaryOfCustomerTransactions(purchaseService, customerService, jas));
        System.out.println(viewStock(cupService));
        System.out.println(purchaseHistory(purchaseService));

        CupRepository cupRepositoryFileBased = new CupRepositoryFileBased();
        cupRepositoryFileBased.saveCup(cup1);
        cupRepositoryFileBased.saveCup(cup2);
        cupRepositoryFileBased.saveCup(cup3);

        System.out.println(cupRepositoryFileBased.findCups());
        cupRepositoryFileBased.updateCup(1, new Cup(1, "no", "no", BigDecimal.valueOf(1.22)));
        System.out.println(cupRepositoryFileBased.findCups());
    }

    public static void addCustomer(CustomerService customerService, Customer customer) {
        customerService.addCustomer(customer);
        System.out.println("Customer " + customer.getName() + " added to shop's database");
    }

    public static void addCupToBasket(CupService cupService, List<Cup> orderList, Cup cup) {
        System.out.println("====add to Basket processing....====");
        Optional<Cup> searchedCup = cupService.showAllCups().stream()
                .filter(c -> c.getId() == cup.getId())
                .findFirst();
        if (searchedCup.isPresent()) {
            cupService.sellCup(searchedCup.get());
            orderList.add(searchedCup.get());
        } else {
            throw new NoSuchElementException("Cup currently unavailable");
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

    public static List<Purchase> purchaseHistory(PurchaseService purchaseService) {
        System.out.println("====All Purchases (history)======\n");
        return purchaseService.AllPurchasesHistory();
    }
}
