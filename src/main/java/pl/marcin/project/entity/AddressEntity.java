package pl.marcin.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @Column(name = "address_id")
    private Long address_id;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private int number;
    @Column(name = "town")
    private String town;
    @Column(name = "code")
    private String code;

    public AddressEntity(String street, int number, String town, String code) {
        this.street = street;
        this.number = number;
        this.town = town;
        this.code = code;
    }
}
