package space.plan.telemetryservice.service;

import space.plan.telemetryservice.data.entity.TelemetryEntity;

public interface TelemetryService {

    void saveTelemetry(TelemetryEntity telemetryEntity);
}