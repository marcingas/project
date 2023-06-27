package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Cup {
    private int id;
    private String color;
    private String shape;
    private BigDecimal price;


    @Override
    public String toString() {
        return "id: " +id + ", color: " + color +
                ", shape: " + shape +
                ", price: " + price + " USD";
    }
}
