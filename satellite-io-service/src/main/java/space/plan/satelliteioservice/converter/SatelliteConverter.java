package space.plan.satelliteioservice.converter;

import org.springframework.stereotype.Component;
import space.plan.satelliteioservice.data.dto.SatelliteDto;
import space.plan.satelliteioservice.data.entity.SatelliteEntity;

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