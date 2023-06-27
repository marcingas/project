package pl.marcin.project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
public class Cup {
    private int id;
    private String color;
    private String shape;
    private BigDecimal price;

    public Cup(int id, String color, String shape, BigDecimal price) {
        this.id = id;
        this.color = color;
        this.shape = shape;
        this.price = price;
    }

    @Override
    public String toString() {
        return "id: " +id + ", color: " + color +
                ", shape: " + shape +
                ", price: " + price + " USD";
    }
}
