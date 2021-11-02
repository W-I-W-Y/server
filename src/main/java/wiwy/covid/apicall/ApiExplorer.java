package wiwy.covid.apicall;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import wiwy.covid.apicall.abroadcoronadto.AbrCoronaDto;
import wiwy.covid.apicall.abroadcoronadto.AbrResponse;
import wiwy.covid.apicall.clinicdto.Clinic;
import wiwy.covid.apicall.coronadto.CoronaData;
import wiwy.covid.apicall.coronadto.Response;
import wiwy.covid.apicall.dismsgdto.DisasterMsg;
import wiwy.covid.apicall.repository.*;
import wiwy.covid.apicall.vaccinedto.Vaccine;

import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApiExplorer {

    private final CoronaRepository coronaRepository;
    private final DisMsgRepository disMsgRepository;
    private final VaccineRepository vaccineRepository;
    private final ClinicRepository clinicRepository;
    private final AbrCoronaRepository abrCoronaRepository;
    private ObjectMapper xmlMapper = new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    private ObjectMapper xmlMapper = new XmlMapper();

    @Transactional
    public void fetching(String startDate, String endDate) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
//        log.debug("sb.toString() = {}",sb.toString());

        Response response= xmlMapper.readValue(sb.toString(), Response.class);

        if(response.getBody().getTotalCount() != 0 && response.getHeader().getResultCode() == 0) {
            if(response.getBody().getItems() != null) {
                List<CoronaData> items = response.getBody().getItems();
                validateCoronaData(items);
            }
        } else {
            log.warn("Corona Data API ERROR");
        }
    }

    private void validateCoronaData(List<CoronaData> items) {
        List<CoronaData> all = coronaRepository.findAll();
        if (all.isEmpty()) {
            for (CoronaData item : items) {
                coronaRepository.save(item);
            }
            return;
        }
        CoronaData recentCorona = coronaRepository.findFirstByOrderByIdDesc();

        if(recentCorona == null) {
            throw new IllegalStateException("validateCoronaData : recentCorona가 존재하지 않습니다.");
        }
        if (recentCorona.getStdDay().equals(items.get(0).getStdDay())) { // 오늘 데이터를 가지고 있음
            log.info("ApiExplorer : validateCoronaData : 당일 코로나데이터가 중복으로 존재하여 DB에 저장하지 않습니다.");
            return;
        }
        for (CoronaData item : items) {
            coronaRepository.save(item);
        }
    }

    @Transactional
    public void updateDisMsg() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1741000/DisasterMsg3/getDisasterMsg1List"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("인증키", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*호출문서 형식*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        DisasterMsg disMsgTotal = xmlMapper.readValue(sb.toString(), DisasterMsg.class);
        if(disMsgTotal.getHead().getTotalCount() != 0) {
            log.debug("disMsgTotal = {}", disMsgTotal.getHead().getTotalCount());
            log.debug("disMSg = {}", disMsgTotal.getRow().getMd101_sn());
//            log.debug("getRows = {}", disMsgTotal.getRows());
//            if(disMsgTotal.getRows() != null && !disMsgTotal.getRows().isEmpty()) {
//                List<DisMsg> rows = disMsgTotal.getRows();
//                log.debug("getRows = {}", rows);
//                Integer currentSN = rows.get(0).getMd101_sn();
//                if(currentSN == null) {
//                    currentSN = 0;
//                }
//                validateDisMsgSN(rows, currentSN);
//            }
        }
    }

//    private void validateDisMsgSN(List<DisMsg> rows, Integer currentSN) {
//        List<DisMsg> recentDisMsg = disMsgRepository.findRecentDisMsg();
//        // 재난문자가 DB에 아예 없을 때 -> 초기상태
//        if(recentDisMsg == null) {
//            for (DisMsg disMsg : recentDisMsg) {
//                disMsgRepository.save(disMsg);
//            }
//            return;
//        }
//        int recentSN = recentDisMsg.get(0).getMd101_sn();
//        int validateSN = currentSN - recentSN;
//
//        if(validateSN != 0) {
//            for (int i = 0 ; i < validateSN ; i++) {
//                disMsgRepository.save(rows.get(i));
//            }
//        }
//    }

    @Transactional
    public void updateVaccine() throws IOException{
        String vaccineURL = "https://nip.kdca.go.kr/irgd/cov19stats.do?list=all";
        URL url = new URL(vaccineURL);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
//        System.setProperty("https.protocols", "TLSv1");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

//        System.out.println(sb.toString());

        wiwy.covid.apicall.vaccinedto.Response response = xmlMapper.readValue(sb.toString(), wiwy.covid.apicall.vaccinedto.Response.class);
        List<Vaccine> items = response.getBody().getItems();
        if(items != null) {
            validateVaccine(items);
        }


    }

    private void validateVaccine(List<Vaccine> items) {
        List<Vaccine> all = vaccineRepository.findAll();
        Vaccine findVaccine = vaccineRepository.findFirstByTpcd("전체건수(C): (A)+(B)", Sort.by("firstCnt").descending());

        if (all.isEmpty()) {
            for (Vaccine vaccine : items) {
                vaccineRepository.save(vaccine);
            }
            return;
        }

        if (findVaccine == null) {
            throw new IllegalStateException("validateVaccine : findVaccine 가 존재하지 않습니다.");
        }

        Integer compareCnt = 0;
        for (Vaccine item : items) {
            if (item.getTpcd().equals("전체건수(C): (A)+(B)")) {
                if (findVaccine.getFirstCnt().equals(item.getFirstCnt())) {
                    return;
                }
            }
        }
        for (Vaccine item : items) {
            vaccineRepository.save(item);
        }
    }

    @Transactional
    public void fetchingAbrCorona(String startDate, String endDate) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19NatInfStateJson"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("startCreateDt","UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")); /*검색할 생성일 범위의 시작*/
        urlBuilder.append("&" + URLEncoder.encode("endCreateDt","UTF-8") + "=" + URLEncoder.encode(endDate, "UTF-8")); /*검색할 생성일 범위의 종료*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        log.debug("sb.toString() = {}",sb.toString());

        AbrResponse abrResponse= xmlMapper.readValue(sb.toString(), AbrResponse.class);

        if(abrResponse.getBody().getTotalCount() != 0 && abrResponse.getHeader().getResultCode() == 0) {
            if(abrResponse.getBody().getItems() != null) {
                List<AbrCoronaDto> items = abrResponse.getBody().getItems();
                validateAbrCoronaData(items);
            }
        } else {
            log.warn("Abroad Corona Data API ERROR");
        }
    }

    private void validateAbrCoronaData(List<AbrCoronaDto> items) {
        List<AbrCoronaDto> all = abrCoronaRepository.findAll();
        Optional<AbrCoronaDto> recentAbrCorona = abrCoronaRepository.findFirstByOrderByIdDesc();

        if (all.isEmpty()) {
            for (AbrCoronaDto item : items) {
                abrCoronaRepository.save(item);
            }
            return;
        }


        if(recentAbrCorona.isEmpty()) {
            throw new IllegalStateException("validateAbrCoronaData : recentAbrCorona 가 존재하지 않습니다.");
        } else {
            String stdDay = recentAbrCorona.get().getStdDay();
            String cp = stdDay.substring(0, stdDay.length() - 4);
            if (items.get(0).getStdDay().contains(cp)) {
                // 오늘 데이터가 이미 업데이트 됨 -> 더 받으면 중복
                log.info("ApiExplorer : validateAbrCoronaData : 당일 해외코로나데이터가 중복으로 존재하여 DB에 저장하지 않습니다.");
                return;
            }
        }


        for (AbrCoronaDto item : items) {
            abrCoronaRepository.save(item);
        }
    }


    @Transactional
    public void fetchClinic() throws IOException {
//        http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList?serviceKey=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D&pageNo=1&numOfRows=10&spclAdmTyCd=A0
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=PPcz55RLIRHgBc%2B5Kzjvbqey%2BsWKrDNmUGNinjzzMcrOygzB%2FI8Tin7bENsGHgDV9puW%2BxpcymvgAU79Rl8S5Q%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("700", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("spclAdmTyCd","UTF-8") + "=" + URLEncoder.encode("99", "UTF-8")); /*A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        log.debug("sb.toString() = {}",sb.toString());

        wiwy.covid.apicall.clinicdto.Response response= xmlMapper.readValue(sb.toString(), wiwy.covid.apicall.clinicdto.Response.class);

        if(response.getBody().getTotalCount() != 0 && response.getHeader().getResultCode().equals("00")) {
            if(response.getBody().getItems() != null) {
                List<Clinic> items = response.getBody().getItems();
                for (Clinic item : items) {
                    clinicRepository.save(item);
                }
            }
        } else {
            log.warn("Clinic fetch ERROR");
        }

    }

}