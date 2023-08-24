package com.pro.infomate.calendar.api;


import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.exception.NotAuthenticationServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class ServerApiService {

//    @Value("${second.server.host}")
    private String SECOND_SERVER_HOST;

//    @Value("${second.server.port}")
    private String SECOND_SERVER_POST;

//    @Value("${second.server.protocol}")
    private String SECOND_SERVER_PROTOCOL;

//    @Value("${second.server.api-token}")
    private List<String> SECOND_SERVER_API_TOKEN;

    private final RestTemplate restTemplate;

    public ServerApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void scheduleInsertApi(ScheduleDTO scheduleDTO) {
        String uri = SECOND_SERVER_PROTOCOL + "://" + SECOND_SERVER_HOST + ":" + SECOND_SERVER_POST + "/calendar/alert";
        log.info("[ServerApiService](scheduleInsertApi) uri : {}", uri);

        restTemplate.exchange(
                uri,
                HttpMethod.POST,
                ResponseEntity.ok().body(
                        CalendarAlertDTO.builder()
                                .scheduleId(scheduleDTO.getId())
                                .scheduleTitle(scheduleDTO.getTitle())
                                .memberCode(2)
                                .calendarName("")
                                .endDate(scheduleDTO.getEndDate())
                                .important(scheduleDTO.getImportant())
                                .build()),
//                String.class
                ScheduleDTO.class
        );
    }

    @PostMapping("/server/userinfo")
    public TokenDTO getUserInfo(@RequestBody TokenDTO tokenDTO, @RequestHeader Map<String, String>  header){
        log.info("[ServerApiService](getUserInfo) : header {}", header);
        log.info("[ServerApiService](getUserInfo) : tokenDTO {}", tokenDTO);
        log.info("[ServerApiService](getUserInfo) : SECOND_SERVER_API_TOKEN {}", SECOND_SERVER_API_TOKEN);

        if(!SECOND_SERVER_API_TOKEN.contains(header.get("www-authenticate"))) throw new NotAuthenticationServer("허용되지 않는 서버 입니다.");

        // memberservice 호출 후 계정 찾아 받환

        return TokenDTO.builder().build();
    }


}
