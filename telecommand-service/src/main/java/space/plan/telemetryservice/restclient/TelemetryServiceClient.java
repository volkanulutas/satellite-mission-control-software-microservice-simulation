package space.plan.telemetryservice.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "telemetry-service")
public interface TelemetryServiceClient {
    @GetMapping("tm/{name}")
    String hello(@PathVariable String name);
}