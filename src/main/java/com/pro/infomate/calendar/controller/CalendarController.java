package com.pro.infomate.calendar.controller;


import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // test success
    @GetMapping("/list/{memberId}")
    public ResponseEntity<ResponseDTO> findAll(@PathVariable int memberId){
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.findAll(memberId))
                        .build());
    }

    // test success
    @GetMapping("/{calendarId}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Integer calendarId){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.findById(calendarId))
                        .build());
    }

    // test success
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> saveByCalendar(@RequestBody CalendarDTO calendar){
        log.info(calendar.toString());
        calendar.setCreateDate(LocalDateTime.now());
        calendarService.saveByCalendar(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    // test success
    @PatchMapping("/update/{calendarId}")
    public ResponseEntity<ResponseDTO> updateByCalendar(@PathVariable Integer calendarId, @RequestBody CalendarDTO calendar){
        calendarService.updateById(calendarId, calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }
    @PutMapping("/updateDafault/{calendarId}")
    public ResponseEntity<ResponseDTO> updateDefaultByCalendar(@PathVariable Integer calendarId, @RequestParam Integer userId){
        calendarService.updateDefaultCalender(calendarId, userId); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    // test successs
    @DeleteMapping("/delete/{calendarId}")
    public ResponseEntity<ResponseDTO> deleteByCalendar(@PathVariable Integer calendarId){
        calendarService.deleteById(calendarId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }


}
