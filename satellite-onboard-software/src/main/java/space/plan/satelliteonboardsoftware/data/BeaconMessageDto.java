package space.plan.satelliteonboardsoftware.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeaconMessageDto implements Serializable {
    private long uptimeInSeconds; // uint32_t

    private float batteryVoltage; // float, in volts 0 - 14.5 volts assumed.

    private int temperature; // int32_t // TODO: should be in float, requirement check.

    private long totalTxMessageCount; // uint32_t

    private long totalRxMessageCount; // uint32_t

    private SatelliteDto satellite;
}