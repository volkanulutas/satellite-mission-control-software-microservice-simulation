package space.plan.satelliteioservice.service;

import space.plan.satelliteioservice.data.entity.RawTelemetryEntity;

public interface SatelliteIoService {

    void saveRawTelemetry(RawTelemetryEntity rawTelemetry);
}