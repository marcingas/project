package pl.marcin.project.tomtomgeoservice.geocodingmodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PositionsConverter {
    Position positionStart;
    Position positionDest;

    @Override
    public String toString() {
        return positionStart + ":" + positionDest;
    }
}
