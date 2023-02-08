package space.plan.telecommandservice.service;

import org.springframework.stereotype.Service;
import space.plan.telecommandservice.data.dto.TelecommandDto;

@Service
public interface TelecommandService {
    void sendData(); // mock

    void sendTelecommad(TelecommandDto telecommand);
}