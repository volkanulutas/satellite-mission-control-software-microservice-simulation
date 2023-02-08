package space.plan.telecommandservice.data.dto;

import lombok.Data;
import space.plan.telecommandservice.data.entity.SatelliteEntity;

import java.io.Serializable;

@Data
public class TelecommandDto implements Serializable {
    private Long id;

    private String name;

    private String definition;

    private long transmissionTimestamp;

    private SatelliteEntity satellite;
}