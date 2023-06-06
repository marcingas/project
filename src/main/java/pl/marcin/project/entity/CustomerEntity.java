package pl.marcin.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder

public class CustomerEntity {
    @Id
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @Column(name = "customer_id")
    private Long customer_id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;
    @OneToMany(mappedBy = "customer")

    private List<PurchaseEntity> purchaseHistory;
}
