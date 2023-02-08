package space.plan.satelliteonboardsoftware.config.rabbitmq;

import com.rabbitmq.client.impl.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.satelliteonboardsoftware.config.appconfig.AppProperty;

import java.time.Duration;

@Component
public class PlanSCredentialsProvider implements CredentialsProvider {
    private final AppProperty appProperty;

    @Autowired
    public PlanSCredentialsProvider(AppProperty appProperty) {
        this.appProperty = appProperty;
    }

    @Override
    public String getUsername() {
        return appProperty.getRabbitMqUser();
    }

    @Override
    public String getPassword() {
        return appProperty.getRabbitMqPassword();
    }

    @Override
    public Duration getTimeBeforeExpiration() {
        return CredentialsProvider.super.getTimeBeforeExpiration();
    }

    @Override
    public void refresh() {
        CredentialsProvider.super.refresh();
    }
}