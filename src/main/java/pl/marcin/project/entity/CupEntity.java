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
    @ManyToOne
    @JoinColumn(name = "purchase_id",referencedColumnName = "purchase_id")
    private PurchaseEntity purchase;
}
