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

//    @Scheduled(fixedRate = 1000*60*5)
//    @Scheduled(cron = "0/30 * * * * *")   // 30초마다
    @Scheduled(cron = "0 0/30 * * * *") // 30분마다
    public void renewingData() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime curTime = LocalDateTime.now();
        String curDate;
        if(curTime.getHour() < 9) {
            curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()-1).format(DateTimeFormatter.BASIC_ISO_DATE);
        } else {
            curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        log.debug("curDate = {}", curDate);

        log.info("renew start");
        apiExplorer.fetching(curDate,curDate);
//        apiExplorer.fetching("20210501","20210528");
//        apiExplorer.fetching("20210502","20210502");
//        apiExplorer.fetching("20210503","20210503");
        log.info("renew end");
    }

//    @Scheduled(cron = "0/30 * * * * *")   // 30초마다
    @Scheduled(cron = "0 0/30 * * * *") // 30분마다
    public void renewingAbrCoronaData() throws IOException {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime curTime = LocalDateTime.now();
        String curDate;
        if(curTime.getHour() < 9) {
            curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()-1).format(DateTimeFormatter.BASIC_ISO_DATE);
        } else {
            curDate = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);
        }
        log.debug("curDate = {}", curDate);

        log.info("renew start");
        apiExplorer.fetchingAbrCorona(curDate,curDate);
//        apiExplorer.fetching("20210501","20210501");
//        apiExplorer.fetching("20210502","20210502");
//        apiExplorer.fetching("20210503","20210503");
        log.info("renew end");
    }
}
