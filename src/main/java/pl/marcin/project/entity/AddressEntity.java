package pl.marcin.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class AddressEntity {
    @Id
    @Column("address_id")
    private Long addressId;
    private String street;
    private Integer number;
    private String town;
    private String code;

    public AddressEntity(String street, Integer number, String town, String code) {
        this.street = street;
        this.number = number;
        this.town = town;
        this.code = code;
    }
}
