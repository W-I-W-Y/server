package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class RenewCorona {

    private final ApiExplorer apiExplorer;

    @Scheduled(cron = "0 0/30 * * * *") // 30분마다
    public void renewingData() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime curTime = LocalDateTime.now();
        String curDate;
        if(curTime.getHour() < 9) {
            curDate = currentDate.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        } else {
            curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        log.debug("curDate = {}", curDate);

        log.info("renew start");
        apiExplorer.fetching(curDate,curDate);
        log.info("renew end");
    }

    @Scheduled(cron = "0 0/30 * * * *") // 30분마다
    public void renewingAbrCoronaData() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime curTime = LocalDateTime.now();
        String curDate;
        if(curTime.getHour() < 9) {
            curDate = currentDate.minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE);
        } else {
            curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        log.debug("curDate = {}", curDate);

        log.info("renew start");
        apiExplorer.fetchingAbrCorona(curDate,curDate);
        log.info("renew end");
    }

//    @Scheduled(cron = "0 0/1 * * * *") // 30분마다
    public void renewClinic() throws IOException {

        log.info("fetching clinic start");
//        apiExplorer.fetchClinic();
        log.info("fetching clinic end");
    }
}
