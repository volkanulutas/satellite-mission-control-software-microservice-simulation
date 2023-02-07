package space.plan.telemetryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.plan.telemetryservice.restclient.TelemetryServiceClient;

@RestController
@RequestMapping("/tc")
public class TelecommandController {
    private TelemetryServiceClient telemetryServiceClient;

    @Autowired
    public TelecommandController(TelemetryServiceClient telemetryServiceClient) {
        this.telemetryServiceClient = telemetryServiceClient;
    }

    @GetMapping("/{name}")
    public String hello(@PathVariable("name") String name) {
        String hello = telemetryServiceClient.hello(name);
        return hello;
    }
}