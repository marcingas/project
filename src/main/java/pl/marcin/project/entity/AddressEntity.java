package pl.marcin.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class AddressEntity {
    @Id
    private Long address_id;
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
