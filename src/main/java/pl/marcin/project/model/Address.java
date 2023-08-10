package pl.marcin.project.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Address {

    private Integer address_id;
    private String street;
    private Integer number;
    private String town;
    private String code;
}
