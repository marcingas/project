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
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private Long id;
    private String name;
    private String surname;
    @Column("address_id")
    private Long addressId;


}
