package pl.marcin.project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="address")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressEntity {
    @Id
    @SequenceGenerator(name= "address_seq", sequenceName = "address_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @Column(name="address_id")
    private Long address_id;
    @Column(name = "street")
    private String street;
    @Column(name="number")
    private int number;
    @Column(name = "town")
    private String town;
    @Column(name = "code")
    private String code;
}
