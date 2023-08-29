package com.pro.infomate.calendar.controller;


import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.service.CalendarService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping(value = "/list/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> findAll(@PathVariable int memberCode){
        log.info("[CalendarController](findAll) memberCode : {}",memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.findAll(memberCode, 2))
                        .build());
    }


    @GetMapping("/mylist/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> myCalendarList(@PathVariable int memberCode){

        log.info("[CalendarController](myCalendarList) memberCode : {}",memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.myCalendarList(memberCode, 2))
                        .build());
    }


    // test success 안씀
//    @GetMapping("/{calendarId}")
//    public ResponseEntity<ResponseDTO> findById(@PathVariable Integer calendarId){
//        log.info("[CalendarController](findById) calendarId : {}",calendarId);
//
//        return ResponseEntity.ok()
//                .body(ResponseDTO.builder()
//                        .status(HttpStatus.OK)
//                        .message("success")
//                        .data(calendarService.findById(calendarId))
//                        .build());
//    }


    @PostMapping("/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> saveByCalendar(@RequestBody CalendarDTO calendar){
        log.info("[CalendarController](saveByCalendar) calendar : {}",calendar);

        calendar.setCreateDate(LocalDateTime.now());

        log.info("[CalendarController](saveByCalendar) calendar : {}",calendar);

        calendarService.saveByCalendar(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더가 정상적으로 생성됐습니다.")
                        .build());
    }


    @PatchMapping("/update") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateByCalendar(@RequestBody CalendarDTO calendar){

        log.info("[CalendarController](updateByCalendar) calendar : {}",calendar);

        calendarService.updateById(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정되었습니다.")
                        .build());
    }

    @PatchMapping("/updateDafault") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateDefaultByCalendar(@RequestBody CalendarDTO calendarDTO){
        log.info("[CalendarController](updateDefaultByCalendar) calendarDTO: {} ",calendarDTO);
        calendarService.updateDefaultCalender(calendarDTO); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("기본 캘린더가 변경되었습니다.")
                        .build());
    }

    @PatchMapping("/changeIndexNo") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateCalendarIndexNo(@RequestBody Map<String, String> info){
        log.info("[CalendarController](updateDefaultByCalendar) info: {} ",info);
        calendarService.updateCalendarIndexNo(info); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더 순서가 변경되었습니다")
                        .build());
    }

    @DeleteMapping("/delete") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteByCalendar(@RequestBody List<Integer> calendarList){
        log.info("[CalendarController](deleteByCalendar) calendarList : ", calendarList);

        calendarService.deleteById(calendarList);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더가 정상적으로 삭제 되었습니다.")
                        .build());
    }

    @GetMapping("/openCalendarList/{memberCode}") // api 연동 화인
    public ResponseEntity<ResponseDTO> findOpenCalendarList(@PathVariable Integer memberCode,
                                                            Pageable pageable ){

        log.info("[CalendarController](findOpenCalendarList) pageable : {}", pageable);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("공개 상태가 수정됐습니다.")
                        .data(calendarService.openCalendarList(memberCode, pageable))
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
