package space.plan.satelliteioservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.plan.satelliteioservice.data.entity.RawTelemetryEntity;

public interface RawTelemetryRepository extends JpaRepository<RawTelemetryEntity, Long> {
}