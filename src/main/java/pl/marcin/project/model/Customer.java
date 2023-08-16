package pl.marcin.project.model;


import lombok.*;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Customer {
    private Integer customerId;
    private String name;
    private String surname;
    private String address;

}
