package space.plan.telecommandservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import space.plan.telecommandservice.restclient.TelemetryServiceClient;
import space.plan.telecommandservice.service.TelecommandService;

@RestController
@RequestMapping("/tc")
public class TelecommandController {
    private final TelemetryServiceClient telemetryServiceClient;

    private final TelecommandService telecommandService;

    @Autowired
    public TelecommandController(TelemetryServiceClient telemetryServiceClient, TelecommandService telecommandService) {
        this.telemetryServiceClient = telemetryServiceClient;
        this.telecommandService = telecommandService;
    }

    @GetMapping("/{name}")
    public String hello(@PathVariable("name") String name) {
        String hello = telemetryServiceClient.hello(name);
        telecommandService.sendData();
        return hello;
    }
}