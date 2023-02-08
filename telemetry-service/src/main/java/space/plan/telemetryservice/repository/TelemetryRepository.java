package space.plan.telemetryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.plan.telemetryservice.data.entity.BeaconEntity;

public interface TelemetryRepository extends JpaRepository<BeaconEntity, Long> {
}