package space.plan.telemetryservice.data.entity;

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
@Table(name="TELEMETRY")
public class TelemetryEntity {
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

    @ManyToOne
    private SatelliteEntity satellite;
}
