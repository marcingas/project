package pl.marcin.project.model;

import java.math.BigDecimal;
import java.util.List;

public class Purchase {
    private int id;
    private Customer customer;
    private List<Cup>cups;
    private BigDecimal purchaseCost;

    public Purchase(Customer customer, List<Cup> cups, BigDecimal purchaseCost) {
        this.customer = customer;
        this.cups = cups;
        this.purchaseCost = purchaseCost; //implement discount
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }
}