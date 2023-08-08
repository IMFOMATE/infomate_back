package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

//    private final TestService testService;

    // test success
    // 조건 추가 예정
    @GetMapping("/all/{memberCode}") // 월,주,일 단위 출력(?)예정 , 회사, 부서 일정 추가 예정
    public ResponseEntity<ResponseDTO>findAllSchedule(@PathVariable Integer memberCode){
        log.info("[ScheduleController](findAllSchedule) memberCode : {} ", memberCode);

        List<ScheduleDTO> scheduleList = scheduleService.findAllScheduleByCalendarByMemberCode(memberCode);
        log.info("[ScheduleController](findAllSchedule) scheduleList : {} ", scheduleList);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(scheduleList)
                        .build());
    }

    // test success
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Integer scheduleId){
        log.info("[ScheduleController](findById) scheduleId : {} ", scheduleId);

        ScheduleDTO schedule = scheduleService.findById(scheduleId);
        log.info("[ScheduleController](findById) schedule : {} ", schedule);

        return ResponseEntity.ok()
                    .body(ResponseDTO.builder()
                            .status(HttpStatus.OK.value())
                            .message("success")
                            .data(schedule)
                            .build());
    }

    // test success
    @PatchMapping("/update")
    public ResponseEntity<ResponseDTO> updateById(@RequestBody ScheduleDTO scheduleDTO){
        log.info("[ScheduleController](updateById) scheduleDTO : {} ", scheduleDTO);
        scheduleService.updateById(scheduleDTO);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("update_success")
                        .build());
    }

    // test success
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Integer scheduleId){
        log.info("[ScheduleController](deleteById) scheduleId : {} ", scheduleId);
        scheduleService.deleteById(scheduleId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("delete_success")
                        .build());
    }


    @GetMapping("/findScheduleSearch")
    public ResponseEntity<ResponseDTO> findScheduleSearch(String keyword){
        Integer userId = 1;
        List<ScheduleDTO> scheduleList = scheduleService.findScheduleSearch(keyword, userId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(scheduleList)
                        .build());
    }

    // test success
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO){
        log.info("[ScheduleController](insertSchedule) scheduleDTO : {} ", scheduleDTO);

//        testService.testInsertApi(scheduleDTO);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(scheduleService.insertSchedule(scheduleDTO))
                        .build());
    }
}
