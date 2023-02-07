package space.plan.satelliteioservice.service;

import com.netflix.discovery.provider.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.plan.satelliteioservice.data.entity.RawTelemetryEntity;
import space.plan.satelliteioservice.repository.RawTelemetryRepository;

@Service
public class SatelliteIoServiceImpl implements SatelliteIoService{
    private final RawTelemetryRepository rawTelemetryRepository;
    @Autowired
    public SatelliteIoServiceImpl(RawTelemetryRepository rawTelemetryRepository) {
        this.rawTelemetryRepository = rawTelemetryRepository;
    }

    @Override
    public void saveRawTelemetry(RawTelemetryEntity rawTelemetry) {
        rawTelemetryRepository.save(rawTelemetry);
    }
}