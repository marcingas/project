package pl.marcin.project.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


public class Customer {


    private String name;

    private String surname;

    private String address;
    private List<Purchase> purchaseHistory;




}
