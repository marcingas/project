package pl.marcin.project.servicetomtom.routingmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Summary {
    private Integer lengthInMeters;
    private Integer travelTimeInSeconds;
    private Integer trafficDelayInSeconds;

    public Summary(Integer lengthInMeters) {
        this.lengthInMeters = lengthInMeters;
    }
}
