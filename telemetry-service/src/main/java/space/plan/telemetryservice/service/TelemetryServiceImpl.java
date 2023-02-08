package space.plan.telemetryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.plan.telemetryservice.converter.BeaconMessageConverter;
import space.plan.telemetryservice.data.dto.BeaconMessageDto;
import space.plan.telemetryservice.data.entity.BeaconEntity;
import space.plan.telemetryservice.repository.TelemetryRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class TelemetryServiceImpl implements TelemetryService {
    private final TelemetryRepository telemetryRepository;

    private final BeaconMessageConverter beaconMessageConverter;

    private final ThreadPoolExecutor telemetrySaveExecutor;

    @Autowired
    public TelemetryServiceImpl(TelemetryRepository telemetryRepository, BeaconMessageConverter beaconMessageConverter) {
        this.telemetryRepository = telemetryRepository;
        this.beaconMessageConverter = beaconMessageConverter;
        telemetrySaveExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
    }

    @Override
    public void saveTelemetry(BeaconEntity beaconEntity) {
        telemetryRepository.save(beaconEntity);
    }

    @Override
    public void processTelemetry(BeaconMessageDto beaconMessageDto) {
        telemetrySaveExecutor.execute(() -> {saveTelemetry(beaconMessageConverter.toEntity(beaconMessageDto));});
        // send to UI
    }
}
