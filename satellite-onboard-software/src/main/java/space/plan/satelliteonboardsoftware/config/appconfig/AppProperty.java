package space.plan.satelliteonboardsoftware.config.appconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@Getter
@Setter
public class AppProperty {
    private String rabbitMqHost;

    private int rabbitMqPort;

    private String rabbitMqUser;

    private String rabbitMqPassword;
}