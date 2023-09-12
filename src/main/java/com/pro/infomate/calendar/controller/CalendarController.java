package com.pro.infomate.calendar.controller;


import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.service.CalendarService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.PagingResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(tags = "Calendar", description = "캘린더 사이드바 목록", summary = "캘린더 사이드바 목록API")
    @GetMapping(value = "/list")
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

    @Operation(tags = "Calendar", description = "캘린더 환경설정에서 내 캘린더 목록을 가져 올 때 사용", summary = "내 캘린더 목록API")
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
    @Operation(tags = "Calendar", description = "캘린더 등록", summary = "내 캘린더 등록 API")
    @PostMapping("/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> saveByCalendar(@RequestBody CalendarDTO calendar,
                                                      @AuthenticationPrincipal MemberDTO member ){

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

    @Operation(tags = "Calendar", description = "캘린더 이름 라벨컬러 기본캘린더 등 수정 API", summary = "캘린더 수정 API")
    @PatchMapping("/update") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateByCalendar(@RequestBody CalendarDTO calendar,
                                                        @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();

        log.info("[CalendarController](updateByCalendar) calendar : {}",calendar);

        calendarService.updateById(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정되었습니다.")
                        .build());
    }
    @Operation(tags = "Calendar", description = "캘린더 목록중 기본이 되는 캘린더 수정 API", summary = "기본 캘린더 수정 API")
    @PatchMapping("/updateDafault") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateDefaultByCalendar(@RequestBody CalendarDTO calendarDTO,
                                                               @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();

        log.info("[CalendarController](updateDefaultByCalendar) calendarDTO: {} ",calendarDTO);
        calendarService.updateDefaultCalender(calendarDTO, memberCode); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("기본 캘린더가 변경되었습니다.")
                        .build());
    }

    @Operation(tags = "Calendar", description = "캘린더 목록의 순서를 수정하는 API", summary = "내 캘린더 목록 순서 변경API")
    @PatchMapping("/changeIndexNo") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateCalendarIndexNo(@RequestBody Map<String, String> info,
                                                             @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();
        log.info("[CalendarController](updateDefaultByCalendar) info: {} ",info);
        calendarService.updateCalendarIndexNo(info, memberCode); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더 순서가 변경되었습니다")
                        .build());
    }
    @Operation(tags = "Calendar", description = "내 캘린더 삭제 API", summary = "캘린더 삭제 API")
    @DeleteMapping("/delete/{calendar}") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteByCalendar(@PathVariable Integer calendar,
                                                        @AuthenticationPrincipal MemberDTO member ){
        log.info("[CalendarController](deleteByCalendar) member : {} ", member);

        int memberCode = member.getMemberCode();

        log.info("[CalendarController](deleteByCalendar) calendar : {}", calendar);
        log.info("[CalendarController](deleteByCalendar) memberCode : {}", memberCode);

        calendarService.deleteById(calendar, memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("캘린더가 정상적으로 삭제 되었습니다.")
                        .build());
    }

    @Operation(tags = "Calendar", description = "구독 가능한 캘린더 목록을 가져오는 API", summary = "공개된 캘린더 목록 API")
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

//    @Operation(tags = "Calendar", description = "일자별 등록된 일정 갯수를 가져오는 API", summary = "대시보드 미니캘린더 API")
//    @GetMapping("/summary")
//    public ResponseEntity<ResponseDTO> findSummaryCalendar(@AuthenticationPrincipal MemberDTO member){
//
//        int memberCode = member.getMemberCode();
//
//        log.info("[CalendarController](findSummaryCalendar) memberCode : ", memberCode);
//
//        List<CalendarSummaryDTO> calendarSummaryDTOList =
//                calendarService.findSummaryCalendar(memberCode);
//
//        log.info("[CalendarController](findSummaryCalendar) calendarSummaryDTOList : ", calendarSummaryDTOList);
//
//        returㄱn ResponseEntity.ok()
//                .body(ResponseDTO.builder()
//                        .status(HttpStatus.OK)
//                        .message("success")
//                        .data(calendarSummaryDTOList)
//                        .build());
//    }

}
