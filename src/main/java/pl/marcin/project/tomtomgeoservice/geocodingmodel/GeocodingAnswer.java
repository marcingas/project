package pl.marcin.project.tomtomgeoservice.geocodingmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GeocodingAnswer {
    List<Result> results;
}
