package space.plan.telemetryservice.data.dto;

import jakarta.persistence.*;
import lombok.Data;
import space.plan.telemetryservice.data.entity.SatelliteEntity;

@Data
public class TelemetryDto {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String definition;

    @Column
    private long generationTimestamp;

    @Column
    private long receivedTimestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "satellite_id", referencedColumnName = "id")
    private SatelliteEntity satellite;
}