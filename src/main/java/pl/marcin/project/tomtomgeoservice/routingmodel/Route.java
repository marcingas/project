package pl.marcin.project.tomtomgeoservice.routingmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Route {
    @JsonProperty("summary")
    private Summary summary;
}
