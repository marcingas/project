package pl.marcin.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private Integer addressId;
    private String street;
    private Integer number;
    private String town;
    private String code;

}
