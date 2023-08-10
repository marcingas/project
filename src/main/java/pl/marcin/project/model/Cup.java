package pl.marcin.project.model;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Cup {

    private Integer cup_id;
    private String color;
    private String shape;
    private BigDecimal price;


}
