package pl.marcin.project.model;


import lombok.*;
import pl.marcin.project.repository.PurchaseRepositoryListBased;
import pl.marcin.project.service.PurchaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Customer {

    private Integer id;
    private String name;
    private String surname;
    private Address address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return getId() == customer.getId() && getName().equals(customer.getName()) && getSurname()
                .equals(customer.getSurname()) && getAddress().equals(customer.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getAddress());
    }

}
