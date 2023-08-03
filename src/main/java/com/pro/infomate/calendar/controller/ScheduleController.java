package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/all") // 월,주,일 단위 출력(?)
    public ResponseEntity<ResponseDTO>findAllSchedule(Integer calendarId){

        List<ScheduleDTO> scheduleList = scheduleService.findAllScheduleByCalendarId(calendarId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(scheduleList)
                        .build());
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Integer scheduleId){
        ScheduleDTO schedule = scheduleService.findById(scheduleId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(schedule)
                        .build());
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO> updateById(@PathVariable Integer scheduleId, ScheduleDTO schedule){
        scheduleService.updateById(scheduleId, schedule);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable Integer scheduleId){
        scheduleService.deleteById(scheduleId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }


    @GetMapping("/findScheduleSearch")
    public ResponseEntity<ResponseDTO> findScheduleSearch(String keyword){
        Integer userId;
        List<ScheduleDTO> scheduleList = scheduleService.findScheduleSearch(keyword, userId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(scheduleList)
                        .build());
    }

}
