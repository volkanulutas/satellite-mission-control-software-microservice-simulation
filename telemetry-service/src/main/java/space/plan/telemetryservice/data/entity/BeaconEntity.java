package space.plan.telemetryservice.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BEACON")
public class BeaconEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private long uptimeInSeconds;

    @Column
    private float batteryVoltage;

    @Column
    private int temperature;

    @Column
    private long totalTxMessageCount;

    @Column
    private long totalRxMessageCount;

    @ManyToOne
    private SatelliteEntity satellite;
}