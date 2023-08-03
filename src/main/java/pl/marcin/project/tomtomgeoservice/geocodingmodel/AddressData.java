package pl.marcin.project.tomtomgeoservice.geocodingmodel;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressData {

    private String postCode;
    private String town;
    private String street;
    private Integer number;

}
