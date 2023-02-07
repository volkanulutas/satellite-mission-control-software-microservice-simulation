/*
 * (C)2021 Esen System Integration
 * The copyright to the computer program(s) herein is the property of Esen System Integration.
 * The program(s) may be used and/or copied only with the written permission of
 * Esen System Integration or in accordance with the terms and conditions stipulated
 * in the agreement/contract under which the program(s) have been supplied.
 */

package space.plan.telecommandservice.config.rabbitmq;

import com.rabbitmq.client.impl.CredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.plan.telecommandservice.config.appconfig.AppProperty;

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