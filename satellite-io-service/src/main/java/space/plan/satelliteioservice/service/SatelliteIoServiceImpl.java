package space.plan.satelliteioservice.service;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.plan.satelliteioservice.config.rabbitmq.RabbitMqConfiguration;
import space.plan.satelliteioservice.converter.SatelliteConverter;
import space.plan.satelliteioservice.data.dto.BeaconMessageDto;
import space.plan.satelliteioservice.data.dto.SatelliteDto;
import space.plan.satelliteioservice.data.entity.RawTelemetryEntity;
import space.plan.satelliteioservice.repository.RawTelemetryRepository;

import java.util.Base64;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class SatelliteIoServiceImpl implements SatelliteIoService {
    private final RawTelemetryRepository rawTelemetryRepository;

    private final SatelliteConverter satelliteConverter;

    private final RabbitTemplate rabbitTemplate;

    private final ThreadPoolExecutor rawTelemetrySaveExecutor;

    @Autowired
    public SatelliteIoServiceImpl(RawTelemetryRepository rawTelemetryRepository, SatelliteConverter satelliteConverter,
                                  RabbitTemplate rabbitTemplate) {
        this.rawTelemetryRepository = rawTelemetryRepository;
        this.satelliteConverter = satelliteConverter;
        this.rabbitTemplate = rabbitTemplate;
        rawTelemetrySaveExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    }

    @Override
    public void saveRawTelemetry(byte[] encodedBeaconMessage, SatelliteDto satelliteDto) {
        RawTelemetryEntity rawTelemetryEntity = new RawTelemetryEntity();
        rawTelemetryEntity.setSatellite(satelliteConverter.toEntity(satelliteDto));
        rawTelemetryEntity.setRawData(encodedBeaconMessage);
        rawTelemetryEntity.setGenerationTimestamp(System.currentTimeMillis());
        rawTelemetryRepository.save(rawTelemetryEntity);
    }

    @Override
    public BeaconMessageDto decode(byte[] encodedBeaconMessage) {
        byte[] decode = Base64.getDecoder().decode(encodedBeaconMessage);
        String decodedString = new String(decode);

        return ((BeaconMessageDto) SerializationUtils.deserialize(encodedBeaconMessage));
    }

    @Override
    public void sendTelemetryMessage(BeaconMessageDto telemetryMessageDto) {
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.DEFAULT_EXCHANGE, RabbitMqConfiguration.RK_TELEMETRY_MESSAGE, telemetryMessageDto);
    }

    @Override
    public void processRawTelemetry(byte[] encodedBeaconMessage) {
        BeaconMessageDto beaconMessageDto = this.decode(encodedBeaconMessage);
        rawTelemetrySaveExecutor.execute(() -> {
            this.saveRawTelemetry(encodedBeaconMessage, beaconMessageDto.getSatellite());
        });
        this.sendTelemetryMessage(beaconMessageDto);
    }
}
