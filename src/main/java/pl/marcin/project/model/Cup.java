package pl.marcin.project.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Cup {

    private int id;
    private String color;
    private String shape;
    private BigDecimal price;
}
