package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.service.ScheduleService;
import com.pro.infomate.common.ResponseDTO;
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

//    private final ServerApiService serverApiService;

    // test success
    // 조건 추가 예정
    @GetMapping("/all/{memberCode}") // 월,주,일 단위 출력(?)예정 , 회사, 부서 일정 추가 예정
    public ResponseEntity<ResponseDTO>findAllSchedule(@PathVariable Integer memberCode){
        log.info("[ScheduleController](findAllSchedule) memberCode : {} ", memberCode);

        List<ScheduleDTO> scheduleList = scheduleService.findAllScheduleByCalendarByMemberCode(memberCode);
        log.info("[ScheduleController](findAllSchedule) scheduleList : {} ", scheduleList);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
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
                            .status(HttpStatus.OK)
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
                        .status(HttpStatus.OK)
                        .message("update_success")
                        .build());
    }

    // test success
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteById(@RequestBody List<Integer> scheduleId){
        log.info("[ScheduleController](deleteById) scheduleId : {} ", scheduleId);
        scheduleService.deleteById(scheduleId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("delete_success")
                        .build());
    }


    // test join 부섴드 조인 오류
    @GetMapping("/findScheduleSearch/{memberCode}")
    public ResponseEntity<ResponseDTO> findScheduleSearch(@PathVariable Integer memberCode, @RequestParam String keyword){
//        List<ScheduleDTO> scheduleList = scheduleService.findAllScheduleSearch(memberCode, keyword);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
//                        .data(scheduleList)
                        .build());
    }

    // test success
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO){
        log.info("[ScheduleController](insertSchedule) scheduleDTO : {} ", scheduleDTO);

//        serverApiService.scheduleInsertApi(scheduleDTO);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(scheduleService.insertSchedule(scheduleDTO))
                        .build());
    }

    @GetMapping("/reminder/{memberCode}")
    public ResponseEntity<ResponseDTO> reminder(@PathVariable int memberCode){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(scheduleService.reminder(memberCode))
                        .build());
    }
}
