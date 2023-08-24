package pl.marcin.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
@Data
@Builder
public class CustomerEntity {
    @Id
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;

    public CustomerEntity(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public CustomerEntity(Long customerId, String name, String surname) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
    }
}
