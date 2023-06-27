package pl.marcin.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Purchase {

    private Customer customer;
    private List<Cup> cups;
    private BigDecimal purchaseCost;
    private String discountInfo = "";

    public Purchase(Customer customer, List<Cup> cups) {
        this.customer = customer;
        this.cups = cups;
        BigDecimal purchaseCost = BigDecimal.ZERO;
        for (var cup : cups) {
            purchaseCost = purchaseCost.add(cup.getPrice());
        }
        if (customer.getPurchaseHistory().size() >= 3) {
            this.purchaseCost = purchaseCost.multiply(BigDecimal.valueOf(0.9));
            discountInfo = "!!!10% Discount included!!!: ";
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
                "cups: " + cups +
                "\npurchaseCost: " + discountInfo + "" + purchaseCost + " USD" +
                "\ncustomer: " + customer.getName() + "\n";
    }
}
