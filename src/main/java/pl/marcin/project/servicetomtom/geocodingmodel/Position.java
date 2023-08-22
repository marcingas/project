package pl.marcin.project.servicetomtom.geocodingmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    float lat;
    float lon;

    @Override
    public String toString() {
        return lat + "," + lon;
    }
}

