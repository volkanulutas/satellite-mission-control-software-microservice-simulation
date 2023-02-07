package space.plan.telemetryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.plan.telemetryservice.data.entity.TelemetryEntity;
import space.plan.telemetryservice.repository.TelemetryRepository;

@Service
public class TelemetryServiceImpl implements TelemetryService{

    private final TelemetryRepository telemetryRepository;

    @Autowired
    public TelemetryServiceImpl(TelemetryRepository telemetryRepository) {
        this.telemetryRepository = telemetryRepository;
    }

    @Override
    public void saveTelemetry(TelemetryEntity telemetry) {
        telemetryRepository.save(telemetry);
    }
}
