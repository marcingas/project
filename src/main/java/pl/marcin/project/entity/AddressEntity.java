package pl.marcin.project.entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
    private Long addressId;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private int number;
    @Column(name = "town")
    private String town;
    @Column(name = "code")
    private String code;

}
