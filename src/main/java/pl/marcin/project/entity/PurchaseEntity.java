package pl.marcin.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Table(name = "purchase")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseEntity {
    @Id
    @SequenceGenerator(name = "purchase_seq", sequenceName = "purchase_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_seq")
    @Column(name = "purchase_id")
    private Long purchaseId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @JsonIgnore
    private CustomerEntity customer;
    @Column(name = "purchase_cost")
    private BigDecimal purchaseCost;
    @ManyToMany
    @JoinTable(name = "purchase_cups",
            joinColumns = @JoinColumn(name = "purchase_id"),
            inverseJoinColumns = @JoinColumn(name = "cup_id"))
    private List<CupEntity> cups;

    public PurchaseEntity(CustomerEntity customer, BigDecimal purchaseCost, List<CupEntity> cups) {
        this.customer = customer;
        this.purchaseCost = purchaseCost;
        this.cups = cups;
    }
}
