package space.plan.telemetryservice.service;

import space.plan.telemetryservice.data.dto.BeaconMessageDto;
import space.plan.telemetryservice.data.entity.BeaconEntity;

public interface TelemetryService {

    void saveTelemetry(BeaconEntity telemetryEntity);

    void processTelemetry(BeaconMessageDto beaconMessageDto);
}