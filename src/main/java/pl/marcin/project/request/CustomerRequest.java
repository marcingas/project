package pl.marcin.project.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    private String name;
    private String surname;
    private String street;
    private int number;
    private String town;
    private String code;


}
