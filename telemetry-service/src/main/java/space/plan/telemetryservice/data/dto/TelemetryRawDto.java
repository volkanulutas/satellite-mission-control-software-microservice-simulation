package space.plan.telemetryservice.data.dto;

import lombok.Data;
import space.plan.telemetryservice.data.entity.SatelliteEntity;

@Data
public class TelemetryRawDto {
    private byte[] rawData;

    private long generationTimestamp;

    private SatelliteEntity satellite;
}