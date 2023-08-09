package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Cup {

    private Integer id;
    private String color;
    private String shape;
    private BigDecimal price;


}
