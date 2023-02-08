package space.plan.satelliteonboardsoftware.data;

import lombok.Data;

@Data
public class SatelliteDto {
    private String name;

    private String definition;

    public SatelliteDto(String name) {
        this.name = name;
    }
}