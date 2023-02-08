package space.plan.satelliteioservice.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SatelliteDto {
    private String name;

    private String definition;

    public SatelliteDto(String name) {
        this.name = name;
    }
}