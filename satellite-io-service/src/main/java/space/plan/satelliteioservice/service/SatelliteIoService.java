package space.plan.satelliteioservice.service;

import space.plan.satelliteioservice.data.dto.BeaconMessageDto;
import space.plan.satelliteioservice.data.dto.SatelliteDto;

public interface SatelliteIoService {
    void saveRawTelemetry(byte[] encodedBeaconMessage, SatelliteDto satelliteDto);

    BeaconMessageDto decode(byte[] encodedBeaconMessage);

    void sendTelemetryMessage(BeaconMessageDto telemetryMessageDto);

    void processRawTelemetry(byte[] encodedBeaconMessage);
}
