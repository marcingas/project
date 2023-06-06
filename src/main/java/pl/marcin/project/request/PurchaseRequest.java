package pl.marcin.project.request;

import lombok.Getter;
import lombok.Setter;
import pl.marcin.project.entity.CupEntity;
import pl.marcin.project.model.Cup;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PurchaseRequest {
    private BigDecimal cost;
    private List<CupEntity> cups;
}
