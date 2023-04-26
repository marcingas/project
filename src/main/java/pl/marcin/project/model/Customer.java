package pl.marcin.project.model;


import pl.marcin.project.repository.PurchaseRepositoryListBased;
import pl.marcin.project.service.PurchaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return getId() == customer.getId() && getName().equals(customer.getName()) && getSurname().equals(customer.getSurname()) && getAddress().equals(customer.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getAddress());
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nsurname: " + surname +
                "\naddress: " + address;
    }
}
