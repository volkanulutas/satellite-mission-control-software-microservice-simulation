package space.plan.satelliteonboardsoftware.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import space.plan.satelliteonboardsoftware.config.rabbitmq.RabbitMqConfiguration;
import space.plan.satelliteonboardsoftware.data.BeaconMessageDto;
import space.plan.satelliteonboardsoftware.data.SatelliteDto;
import space.plan.satelliteonboardsoftware.util.Constant;
import space.plan.satelliteonboardsoftware.util.constant.BeaconMessageConstants;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
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
        byte[] encodeBeaconMessage = encodeBeaconMessage(beaconMessage);
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.DEFAULT_EXCHANGE, RabbitMqConfiguration.RK_BEACON_MESSAGE, encodeBeaconMessage);
    }

    public byte[] encodeBeaconMessage(BeaconMessageDto beaconMessage) {
        byte[] serialize = SerializationUtils.serialize(beaconMessage);
        return Base64.getEncoder().encode(serialize);
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