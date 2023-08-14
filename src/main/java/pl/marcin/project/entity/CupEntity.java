package pl.marcin.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table(name = "cup")
@NoArgsConstructor
@AllArgsConstructor
public class CupEntity {
    @Column("cup_id")
    private Long cupId;
    private String color;
    private String shape;
    private BigDecimal price;

}
