package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RenewVaccine {

    private final ApiExplorer apiExplorer;
    @Scheduled(cron = "0 0/30 * * * *") // 30분마다
//    @Scheduled(cron = "0 35 9 * * *")
    public void renewingVaccine() throws IOException {
        apiExplorer.updateVaccine();
        log.info("Update Item");
    }
}
