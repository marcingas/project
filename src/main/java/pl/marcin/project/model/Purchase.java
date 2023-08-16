package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {
    private Integer purchaseId;
    private Customer customer;
    private List<Cup> cups;
    private BigDecimal purchaseCost;

}
