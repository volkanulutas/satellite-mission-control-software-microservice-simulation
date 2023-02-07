package space.plan.satelliteioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SatelliteIoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SatelliteIoServiceApplication.class, args);
	}

}
