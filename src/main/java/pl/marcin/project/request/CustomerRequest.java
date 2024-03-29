package pl.marcin.project.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.marcin.project.entity.AddressEntity;
import pl.marcin.project.model.Purchase;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    private String name;
    private String surname;
    private String street;
    private int number;
    private String town;
    private String code;


}
