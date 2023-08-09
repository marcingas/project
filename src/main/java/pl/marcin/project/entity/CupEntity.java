package pl.marcin.project.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table(name = "cup")
public class CupEntity {
    private Long cup_id;
    private String color;
    private String shape;
    private BigDecimal price;

}
