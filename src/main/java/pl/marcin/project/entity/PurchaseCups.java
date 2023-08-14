package pl.marcin.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "purchase_cups")
public class PurchaseCups {
    private Long cup_id;
    private Long purchase_id;
}
