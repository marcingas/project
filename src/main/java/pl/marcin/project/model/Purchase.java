package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Integer id;
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
        if (customer.getPurchaseHistory().size() >= 3) {
            this.purchaseCost = purchaseCost.multiply(BigDecimal.valueOf(0.9));
            System.out.println("!!!10% Discount included!!!: ");
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
                "\npurchaseCost:" + purchaseCost + " USD" +
                "\ncustomer: " + customer.getName() + "\n";
    }
}
