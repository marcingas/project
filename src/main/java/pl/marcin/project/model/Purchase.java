package pl.marcin.project.model;

import java.math.BigDecimal;
import java.util.List;

public class Purchase {

    private Customer customer;
    private List<Cup> cups;
    private BigDecimal purchaseCost;

    public Purchase(Customer customer, List<Cup> cups) {
        this.customer = customer;
        this.cups = cups;
        BigDecimal purchaseCost = BigDecimal.ZERO;
        for (var cup : cups) {
            purchaseCost = purchaseCost.add(cup.getPrice());
        }
        if (customer.getPurchaseHistory().size() > 4) {
            this.purchaseCost = this.purchaseCost.multiply(BigDecimal.valueOf(0.9));
        } else {
            this.purchaseCost = purchaseCost;
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Cup> getCups() {
        return cups;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    @Override
    public String toString() {
        return "Purchase\n" +
                "customer:" + " id: " + customer.getId() + ", " + customer.getName() + " " + customer.getSurname() +
                "\ncups: " + cups +
                "\npurchaseCost: " + purchaseCost;
    }
}
