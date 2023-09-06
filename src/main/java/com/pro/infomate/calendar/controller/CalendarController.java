package com.pro.infomate.calendar.controller;


import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.service.CalendarService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping(value = "/list") // api 연동 확인
    public ResponseEntity<ResponseDTO> findAll(@AuthenticationPrincipal MemberDTO member){
        log.info("[CalendarController](findAll) principal : {}", member);

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        log.info("[CalendarController](findAll) memberCode : {}", memberCode);
        log.info("[CalendarController](findAll) department : {}", department);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.findAll(memberCode, department))
                        .build());
    }


    @GetMapping("/mylist") // api 연동 확인
    public ResponseEntity<ResponseDTO> myCalendarList(@AuthenticationPrincipal MemberDTO member){

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        log.info("[CalendarController](myCalendarList) memberCode : {}",memberCode);
        log.info("[CalendarController](myCalendarList) department : {}",department);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(calendarService.myCalendarList(memberCode, department))
                        .build());
    }

    @PostMapping("/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> saveByCalendar(@RequestBody CalendarDTO calendar, @AuthenticationPrincipal MemberDTO member ){

        calendar.setMemberCode(member.getMemberCode());

        log.info("[CalendarController](saveByCalendar) calendar : {}", calendar);

        calendar.setCreateDate(LocalDateTime.now());

        log.info("[CalendarController](saveByCalendar) calendar : {}", calendar);

        calendarService.saveByCalendar(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더가 정상적으로 생성됐습니다.")
                        .build());
    }


    @PatchMapping("/update") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateByCalendar(@RequestBody CalendarDTO calendar, @AuthenticationPrincipal MemberDTO member){

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        log.info("[CalendarController](updateByCalendar) calendar : {}",calendar);

        calendarService.updateById(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정되었습니다.")
                        .build());
    }

    @PatchMapping("/updateDafault") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateDefaultByCalendar(@RequestBody CalendarDTO calendarDTO, @AuthenticationPrincipal MemberDTO member){

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        log.info("[CalendarController](updateDefaultByCalendar) calendarDTO: {} ",calendarDTO);
        calendarService.updateDefaultCalender(calendarDTO, memberCode); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("기본 캘린더가 변경되었습니다.")
                        .build());
    }

    @PatchMapping("/changeIndexNo") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateCalendarIndexNo(@RequestBody Map<String, String> info, @AuthenticationPrincipal MemberDTO member){

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();
        log.info("[CalendarController](updateDefaultByCalendar) info: {} ",info);
        calendarService.updateCalendarIndexNo(info); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더 순서가 변경되었습니다")
                        .build());
    }

    @DeleteMapping("/delete") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteByCalendar(@RequestBody List<Integer> calendarList, @AuthenticationPrincipal MemberDTO member){
        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        log.info("[CalendarController](deleteByCalendar) calendarList : ", calendarList);
        log.info("[CalendarController](deleteByCalendar) memberCode : ", memberCode);

        calendarService.deleteById(calendarList, memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더가 정상적으로 삭제 되었습니다.")
                        .build());
    }

    @GetMapping("/openCalendarList") // api 연동 화인
    public ResponseEntity<PagingResponseDTO> findOpenCalendarList(Pageable pageable, @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();

        log.info("[CalendarController](findOpenCalendarList) pageable : {}", pageable);

        pageable = PageRequest.of(
                pageable.getPageNumber() - 1 >= 0 ? pageable.getPageNumber() - 1 : 0 ,
                pageable.getPageSize(),
                pageable.getSort());

        Page<CalendarDTO> calendarDTOPage = calendarService.openCalendarList(memberCode, pageable);


        log.info("[CalendarController](findOpenCalendarList) calendarDTOPage.getSize() : {}", calendarDTOPage.getSize());
        log.info("[CalendarController](findOpenCalendarList) calendarDTOPage.getContent() : {}", calendarDTOPage.getContent());

        PageDTO pageDTO = new PageDTO(new Criteria(pageable.getPageNumber(), pageable.getPageSize()),calendarDTOPage.getTotalPages());
        log.info("[CalendarController](findOpenCalendarList) pageDTO : {}", pageDTO);

        return ResponseEntity.ok()
                .body(PagingResponseDTO.builder()
                        .httpStatus(HttpStatus.OK)
                        .message("succes")
                        .pageInfo(pageDTO)
                        .data(calendarDTOPage.getContent())
                        .build());
    }

    // test success
    @GetMapping("/summary")
    public ResponseEntity<ResponseDTO> findSummaryCalendar(@AuthenticationPrincipal MemberDTO member){

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

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



    @GetMapping("/test/{memberCode}")
    public void saveFirstCalendarRegist(@PathVariable int memberCode){
        calendarService.saveFirstCalendarRegist(memberCode);
    }

}
