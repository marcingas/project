package pl.marcin.project.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.marcin.project.model.Customer;

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
    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    private CustomerEntity customer;
    @Column(name = "purchase_cost")
    private BigDecimal purchaseCost;
    @OneToMany(mappedBy = "purchase")
    private List<CupEntity> cups;


}
