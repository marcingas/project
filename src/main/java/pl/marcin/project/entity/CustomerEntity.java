package pl.marcin.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

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
    private List<PurchaseEntity> purchaseHistory;

    public CustomerEntity(String name, String surname, Long addressId) {
        this.name = name;
        this.surname = surname;
        this.addressId = addressId;
    }

    public CustomerEntity(String name, String surname, Long addressId, List<PurchaseEntity> purchaseHistory) {
        this.name = name;
        this.surname = surname;
        this.addressId = addressId;
        this.purchaseHistory = purchaseHistory;
    }
}
