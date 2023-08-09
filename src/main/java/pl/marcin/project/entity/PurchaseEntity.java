package pl.marcin.project.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "purchase")
@Data
@RequiredArgsConstructor
public class PurchaseEntity {
    @Id
    private Long id;
    @Column("customer_id")
    private Long customerId;
    private BigDecimal purchaseCost;
    private List<CupEntity> cups;

}
