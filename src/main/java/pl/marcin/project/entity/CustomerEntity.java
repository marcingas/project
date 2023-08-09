package pl.marcin.project.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@RequiredArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private Long id;
    private String name;
    private String LastName;
    @Column("address_id")
    private Long addressId;
    private List<PurchaseEntity> purchaseHistory;

}
