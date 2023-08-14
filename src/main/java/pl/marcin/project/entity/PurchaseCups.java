package pl.marcin.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "purchase_cups")
public class PurchaseCups {
    @Column("cup_id")
    private Long cupId;
    @Column("purchase_id")
    private Long purchaseId;
}
