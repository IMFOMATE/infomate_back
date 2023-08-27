package com.pro.infomate.calendar.controller;


import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.service.CalendarService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    // test success 팀, 회사전체 캘린더 추가 예정

    @GetMapping(value = "/list/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public ResponseEntity<ResponseDTO> findAll(@PathVariable int memberId){
        log.info("[CalendarController](findAll) memberId : {}",memberId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.findAll(memberId))
                        .build());
    }

    // test success
    @GetMapping("/mylist/{memberCode}")
    public ResponseEntity<ResponseDTO> myCalendarList(@PathVariable int memberCode){
        log.info("[CalendarController](myCalendarList) memberCode : {}",memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.myCalendarList(memberCode))
                        .build());
    }


    // test success
    @GetMapping("/{calendarId}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Integer calendarId){
        log.info("[CalendarController](findById) calendarId : {}",calendarId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.findById(calendarId))
                        .build());
    }


    @PostMapping("/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> saveByCalendar(@RequestBody CalendarDTO calendar){
        log.info("[CalendarController](saveByCalendar) calendar : {}",calendar);

        calendar.setCreateDate(LocalDateTime.now());

        log.info("[CalendarController](saveByCalendar) calendar : {}",calendar);

        calendarService.saveByCalendar(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }


    @PatchMapping("/update") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateByCalendar(@RequestBody CalendarDTO calendar){

        log.info("[CalendarController](updateByCalendar) calendar : {}",calendar);

        calendarService.updateById(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }

    @PatchMapping("/updateDafault") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateDefaultByCalendar(@RequestBody CalendarDTO calendarDTO){
        log.info("[CalendarController](updateDefaultByCalendar) calendarDTO: {} ",calendarDTO);
        calendarService.updateDefaultCalender(calendarDTO); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }

    @DeleteMapping("/delete") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteByCalendar(@RequestBody List<Integer> calendarList){
        log.info("[CalendarController](deleteByCalendar) calendarList : ", calendarList);

        calendarService.deleteById(calendarList);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }


    // test success
    @GetMapping("/openCalendarList")
    public ResponseEntity<ResponseDTO> findOpenCalendarList(){
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.openCalendarList())
                        .build());
    }

    // test success
    @GetMapping("/summary/{memberCode}")
    public ResponseEntity<ResponseDTO> findSummaryCalendar(@PathVariable int memberCode){

        log.info("[CalendarController](findSummaryCalendar) memberCode : ", memberCode);

        List<CalendarSummaryDTO> calendarSummaryDTOList =
                calendarService.findSummaryCalendar(memberCode);

        log.info("[CalendarController](findSummaryCalendar) calendarSummaryDTOList : ", calendarSummaryDTOList);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarSummaryDTOList)
                        .build());
    }

}
