package space.plan.telecommandservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.plan.telecommandservice.data.entity.TelecommandEntity;

public interface TelecommandRepository extends JpaRepository<TelecommandEntity, Long> {}