package pl.marcin.project.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import pl.marcin.project.model.Cup;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "purchase")
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEntity {
    @Id
    private Long id;
    @Column("customer_id")
    private Long customerId;
    @Column("purchase_cost")
    private BigDecimal purchaseCost;
    @Transient
    private List<Cup> cups;

}
