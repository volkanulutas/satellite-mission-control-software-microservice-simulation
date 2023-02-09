package space.plan.satelliteonboardsoftware.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import space.plan.satelliteonboardsoftware.config.rabbitmq.RabbitMqConfiguration;
import space.plan.satelliteonboardsoftware.data.BeaconMessageDto;
import space.plan.satelliteonboardsoftware.util.Constant;
import space.plan.satelliteonboardsoftware.util.constant.BeaconMessageConstants;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@EnableScheduling
@Service
public class SatelliteOnboardSoftwareServiceImpl implements SatelliteOnboardSoftwareService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SatelliteOnboardSoftwareServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = Constant.BEACON_MESSAGE_SEND_PERIOD)
    public void sendPeriodicTelemetryData() {
        BeaconMessageDto beaconMessage = generateBeaconMessageRandomly();
        byte[] encodedData = encodeBeaconMessage(beaconMessage);
        System.err.println("data: " + encodedData);
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.DEFAULT_EXCHANGE, RabbitMqConfiguration.RK_BEACON_MESSAGE, encodedData);
        log.info("Beacon telemetry is sent! ", beaconMessage.toString());
    }

    public byte[] encodeBeaconMessage(BeaconMessageDto beaconMessage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(beaconMessage);
            org.apache.tomcat.util.codec.binary.Base64 base64 = new org.apache.tomcat.util.codec.binary.Base64();
            return base64.encode(data.getBytes());
        } catch (Exception ex) {
            log.error("Encoding error is occurred. Detail: ", ex);
        }
        return null;
    }

    private BeaconMessageDto generateBeaconMessageRandomly() {
        BeaconMessageDto beaconMessage = new BeaconMessageDto();
        beaconMessage.setUptimeInSeconds(System.currentTimeMillis());
        beaconMessage.setBatteryVoltage(generateBatteryVoltage());
        beaconMessage.setTemperature(generateTemperature());
        beaconMessage.setTotalTxMessageCount(generateTotalMessageCount());
        beaconMessage.setTotalRxMessageCount(generateTotalMessageCount());
        return beaconMessage;
    }

    private long generateTotalMessageCount() {
        return ThreadLocalRandom.current()
                .nextLong() * (BeaconMessageConstants.MESSAGE_COUNT_MAX - BeaconMessageConstants.MESSAGE_COUNT_MIN) + BeaconMessageConstants.MESSAGE_COUNT_MIN;
    }

    private float generateBatteryVoltage() {
        float result = ThreadLocalRandom.current()
                .nextFloat() * (BeaconMessageConstants.BATTERY_VOLTAGE_MAX - BeaconMessageConstants.BATTERY_VOLTAGE_MIN) + BeaconMessageConstants.BATTERY_VOLTAGE_MIN;
        if (result >= BeaconMessageConstants.BATTERY_VOLTAGE_MAX) // correct for rounding
        {
            result = Float.intBitsToFloat(Float.floatToIntBits(BeaconMessageConstants.BATTERY_VOLTAGE_MAX) - 1);
        }
        return result;
    }

    private int generateTemperature() {
        return ThreadLocalRandom.current()
                .nextInt() * (BeaconMessageConstants.TEMPERATURE_MAX - BeaconMessageConstants.TEMPERATURE_MIN) + BeaconMessageConstants.TEMPERATURE_MIN;
    }
}