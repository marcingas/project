package pl.marcin.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.model.Cup;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    private Long customerId;
    private BigDecimal cost;
    private List<Long> cupIds;
}
