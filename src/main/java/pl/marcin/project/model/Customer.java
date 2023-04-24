package pl.marcin.project.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


public class Customer {

    private int id;

    private String name;

    private String surname;

    private String address;

    private List<Purchase> purchaseHistory;

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

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nsurname: " + surname;
    }
}
