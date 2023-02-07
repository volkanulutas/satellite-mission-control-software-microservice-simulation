package space.plan.satelliteioservice.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TELEMETRY_RAW")
public class RawTelemetryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private byte[] rawData;

    @Column
    private long generationTimestamp;

    @ManyToOne
    private SatelliteEntity satellite;
}