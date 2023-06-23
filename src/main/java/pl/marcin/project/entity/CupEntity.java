package pl.marcin.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cup")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CupEntity {
    @Id
    @SequenceGenerator(name = "cup_seq", sequenceName = "cup_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cup_seq")
    @Column(name = "cup_id")
    private Long cup_id;
    @Column(name = "color")
    private String color;
    @Column(name = "shape")
    private String shape;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany(mappedBy = "cups")
    @JsonIgnore
    private List<PurchaseEntity> purchases;

    public CupEntity(String color, String shape, BigDecimal price, List<PurchaseEntity> purchases) {
        this.color = color;
        this.shape = shape;
        this.price = price;
        this.purchases = purchases;
    }
}
