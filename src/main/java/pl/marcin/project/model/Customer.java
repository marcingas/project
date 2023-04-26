package pl.marcin.project.model;


import pl.marcin.project.repository.PurchaseRepositoryListBased;
import pl.marcin.project.service.PurchaseService;

import java.util.ArrayList;
import java.util.List;


public class Customer {

    private int id;

    private String name;

    private String surname;

    private String address;
    private List<Purchase> purchaseHistory;



    public Customer(int id, String name, String surname, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.purchaseHistory= new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public List<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }
    public void updatePurchaseHistory(Purchase purchase){
        purchaseHistory.add(purchase);
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nsurname: " + surname +
                "\naddress: " + address;
    }
}
