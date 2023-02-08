package space.plan.telemetryservice.converter;

import org.springframework.stereotype.Component;
import space.plan.telemetryservice.data.dto.SatelliteDto;
import space.plan.telemetryservice.data.entity.SatelliteEntity;

@Component
public class SatelliteConverter {
    public SatelliteEntity toEntity(SatelliteDto source) {
        SatelliteEntity target = new SatelliteEntity();
        target.setName(source.getName());
        return target;
    }

    public SatelliteDto toDto(SatelliteEntity source) {
        SatelliteDto target = new SatelliteDto();
        target.setName(source.getName());
        return target;
    }
}