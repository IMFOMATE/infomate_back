package com.pro.infomate.calendar.api;


import com.pro.infomate.calendar.dto.ScheduleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ServerApiService {

    //    @Value("${second.server.host}")
    private String SECOND_SERVER_HOST;

    //    @Value("${second.server.port}")
    private String SECOND_SERVER_POST;

    //    @Value("${second.server.protocol}")
    private String SECOND_SERVER_PROTOCOL;

    //    @Value("${second.server.api-token}")
    private String SECOND_SERVER_API_TOKEN;

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

//    @GetMapping("/server/userinfo")
//    @ResponseBody
//    public TokenDTO getUserInfo(@RequestBody TokenDTO tokenDTO){
//        if(!tokenDTO.getJwt().equals(SECOND_SERVER_API_TOKEN)) throw new NotAuthenticationServer("허용되지 않는 서버 입니다.");
//        return TokenDTO.builder().build();
//    }


//        WebClient webClient = WebClient.builder()
//                .baseUrl()
//                .build();
//
//        webClient.post()
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue().retrieve().bodyToMono(CalendarAlertDTO.class).block();



//    }

//    public void testApproval(ApprovalResponse approvalDTO){
//        String uri = SECOND_SERVER_PROTOCOL + "://" + SECOND_SERVER_HOST + ":" + SECOND_SERVER_POST + "/approval/insert";
//        log.info("[TestService](testApproval) uri : {}",uri);
//
//        restTemplate.exchange(
//                uri,
//                HttpMethod.POST,
//                ResponseEntity.ok().body(approvalDTO),
//                ApprovalResponse.class
//                String.class
//        );
//
//    }




}
