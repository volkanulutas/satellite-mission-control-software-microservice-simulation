package space.plan.telemetryservice.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.telemetryservice.data.dto.BeaconMessageDto;
import space.plan.telemetryservice.data.entity.BeaconEntity;

@Component
public class BeaconMessageConverter {
    private final String BEACON_TELEMETRY = "beacon_telemetry";

    private final SatelliteConverter satelliteConverter;

    @Autowired
    public BeaconMessageConverter(SatelliteConverter satelliteConverter) {
        this.satelliteConverter = satelliteConverter;
    }

    public BeaconEntity toEntity(BeaconMessageDto source) {
        BeaconEntity target = new BeaconEntity();
        target.setName(BEACON_TELEMETRY);
        target.setUptimeInSeconds(source.getUptimeInSeconds());
        target.setBatteryVoltage(source.getBatteryVoltage());
        target.setTemperature(source.getTemperature());
        target.setTotalRxMessageCount(source.getTotalRxMessageCount());
        target.setTotalTxMessageCount(source.getTotalTxMessageCount());
        target.setSatellite(satelliteConverter.toEntity(source.getSatellite()));
        return target;
    }

    public BeaconMessageDto toDto(BeaconEntity source) {
        BeaconMessageDto target = new BeaconMessageDto();
        target.setUptimeInSeconds(source.getUptimeInSeconds());
        target.setBatteryVoltage(source.getBatteryVoltage());
        target.setTemperature(source.getTemperature());
        target.setTotalRxMessageCount(source.getTotalRxMessageCount());
        target.setTotalTxMessageCount(source.getTotalTxMessageCount());
        target.setSatellite(satelliteConverter.toDto(source.getSatellite()));
        return target;
    }
}