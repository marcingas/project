package pl.marcin.project.controller;

import pl.marcin.project.model.Address;
import pl.marcin.project.model.Cup;
import pl.marcin.project.model.Customer;
import pl.marcin.project.model.Purchase;
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

public class Shop {
    public static final File file = new File("cups.txt");

    public void shopRunner() {
        file.delete();
        PurchaseService purchaseService = new PurchaseService(new PurchaseRepositoryListBased());
        CustomerService customerService = new CustomerService(new CustomerRepositoryListBased());
        CupService cupService = new CupService(new CupRepositoryListBased());

        Customer jas = new Customer(1, "Jaś", "Kowalski",
                new Address(1, "Krakowska", 1, "Krakow", "30-312"));
        Customer stas = new Customer(2, "Staszek", "Buła",
                new Address(2, "Krakowska", 1, "Krakow", "30-312"));
        Customer jola = new Customer(3, "Jola", "Buła",
                new Address(3, "Krakowska", 1, "Krakow", "30-312"));

        Cup cup1 = new Cup(1, "Blue", "square", BigDecimal.valueOf(1.2));
        Cup cup2 = new Cup(2, "Yellow", "circle", BigDecimal.valueOf(3.2));
        Cup cup3 = new Cup(3, "Orange", "long", BigDecimal.valueOf(2.33));
        Cup cup4 = new Cup(4, "White", "thin", BigDecimal.valueOf(2.23));
        Cup cup5 = new Cup(5, "White", "thin", BigDecimal.valueOf(12.23));

        Purchase purchase1 = new Purchase(jas, new ArrayList<>(List.of(cup1, cup2)));
        Purchase purchase2 = new Purchase(stas, new ArrayList<>(List.of(cup3, cup4)));

        addCustomer(customerService, jas);
        addCustomer(customerService, stas);
        addCustomer(customerService, jola);
        addCup(cupService, cup1);
        addCup(cupService, cup2);
        addCup(cupService, cup5);
        addPurchase(purchaseService, purchase1);
        addPurchase(purchaseService, purchase2);

        updateCustomer(customerService, new Customer(1, "Janek", "Kowalski",
                new Address(5, "Krakowska", 1, "Krakow", "30-312")), 1);
        updateCup(cupService, new Cup(1, "BLUE", "square", BigDecimal.valueOf(2.43)), 1);

        System.out.println(getAllCups(cupService));
        getAllCustomers(customerService);
        findAllPurchasesHistory(purchaseService);

        getCupById(cupService, 1);
        getCustomerById(customerService, 2);
        findPurchaseHistoryByCustomerId(purchaseService, 1);

        deleteCupById(cupService, 5);
        deleteCustomerById(customerService, 3);


    }

    public static int addCustomer(CustomerService customerService, Customer customer) {
        return customerService.addCustomer(customer);
    }

    public static int updateCustomer(CustomerService customerService, Customer customer, int id) {
        return customerService.updateCustomer(id, customer);
    }

    public static List<Customer> getAllCustomers(CustomerService customerService) {
        return customerService.getAllCustomers();
    }

    public static Customer getCustomerById(CustomerService customerService, int customerId) {
        return customerService.getCustomer(customerId);
    }

    public static int deleteCustomerById(CustomerService customerService, int customerId) {
        return customerService.deleteCustomer(customerId);
    }

    public static int addCup(CupService cupService, Cup cup) {
        return cupService.addCup(cup);
    }

    public static int updateCup(CupService cupService, Cup cup, int cupId) {
        return cupService.updateCup(cupId, cup);
    }

    public static List<Cup> getAllCups(CupService cupService) {
        return cupService.showAllCups();
    }

    public static Cup getCupById(CupService cupService, int cupId) {
        return cupService.showCup(cupId);
    }

    public static int deleteCupById(CupService cupService, int cupId) {
        return cupService.deleteCup(cupId);
    }

    public static int addPurchase(PurchaseService purchaseService, Purchase purchase) {
        return purchaseService.savePurchase(purchase);
    }

    public static List<Purchase> findPurchaseHistoryByCustomerId(PurchaseService purchaseService, int customerId) {
        return purchaseService.purchaseHistoryByCustomerId(customerId);
    }

    public static List<Purchase> findAllPurchasesHistory(PurchaseService purchaseService) {
        return purchaseService.AllPurchasesHistory();
    }

}
