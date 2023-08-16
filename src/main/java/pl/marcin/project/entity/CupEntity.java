package pl.marcin.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "cup")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CupEntity {
    @Id
    @SequenceGenerator(name = "cup_seq", sequenceName = "cup_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cup_seq")
    @Column(name = "cup_id")
    private Long cupId;
    @Column(name = "color")
    private String color;
    @Column(name = "shape")
    private String shape;
    @Column(name = "price")
    private BigDecimal price;


    public CupEntity(String color, String shape, BigDecimal price) {
        this.color = color;
        this.shape = shape;
        this.price = price;

    }
}
