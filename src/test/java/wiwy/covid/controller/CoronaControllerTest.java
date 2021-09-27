package wiwy.covid.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wiwy.covid.apicall.AbrCoronaRepository;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoronaControllerTest {

    @Autowired
    AbrCoronaRepository abrCoronaRepository;

    @Test
    void 날짜로조회() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy년 MM월 d일");
        String curDate = date.format(df);
        curDate = curDate + " 00시";
        System.out.println("curDate = " + curDate);
        List<AbrCoronaDto> byDate = abrCoronaRepository.findByDate(curDate);

        Assertions.assertThat(byDate.size()).isEqualTo(190);
    }

}