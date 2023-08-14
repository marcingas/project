package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Integer id;
    private Customer customer;
    private BigDecimal purchaseCost;
    private List<Cup> cups;


    public Purchase(Customer customer, List<Cup> cups) {
        this.customer = customer;
        this.cups = cups;
        BigDecimal purchaseCost = BigDecimal.ZERO;
        for (var cup : cups) {
            purchaseCost.add(cup.getPrice());
        }
        this.purchaseCost = purchaseCost;
    }
}
