package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RenewDisMsg {

    private final ApiExplorer apiExplorer;

//    @Scheduled(cron = "0/15 * * * * *")
//    @Scheduled(cron = "0/15 * * * * *")
//    public void renewingDisMsg() throws IOException {
//        apiExplorer.updateDisMsg();
//    }
}
