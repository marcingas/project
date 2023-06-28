package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class Cup {
    private int id;
    private String color;
    private String shape;
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cup cup)) return false;
        return getId() == cup.getId() && getColor().equals(cup.getColor()) && getShape().equals(cup.getShape()) && getPrice().equals(cup.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getColor(), getShape(), getPrice());
    }

    @Override
    public String toString() {
        return "id: " + id + ", color: " + color +
                ", shape: " + shape +
                ", price: " + price + " USD";
    }
}
