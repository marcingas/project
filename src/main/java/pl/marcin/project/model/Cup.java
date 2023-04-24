package pl.marcin.project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


public class Cup {
    private int id;
    private String color;
    private String shape;
    private BigDecimal price;

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getShape() {
        return shape;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "id: " +id + "\ncolor: " + color +
                "\nshape: " + shape +
                "\nprice: " + price;
    }
}