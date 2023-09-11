package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.DayPerCountDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.service.ScheduleService;
import com.pro.infomate.common.ExpendsProps;
import com.pro.infomate.common.ExpendsResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final EmployeeService employeeService;

//    private final ServerApiService serverApiService;

    @GetMapping("/dayCount")
    public ResponseEntity<ResponseDTO> dayPerCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDay,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDay,
            @AuthenticationPrincipal MemberDTO member){

        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        log.info("[ScheduleController](dayPerCount) startDay : {} ", startDay);
        log.info("[ScheduleController](dayPerCount) endDay : {} ", endDay);
        log.info("[ScheduleController](dayPerCount) memberCode : {} ", memberCode);

        List<DayPerCountDTO> perCount = scheduleService.dayPerCount(startDay, endDay, memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(perCount)
                        .build());
    }

    @GetMapping("/{scheduleId}") // api 연동 확인
    public ResponseEntity<ExpendsResponseDTO> findById(@PathVariable int scheduleId, @AuthenticationPrincipal MemberDTO member) {
        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();
        log.info("[ScheduleController](findById) scheduleId : {} ", scheduleId);

        ScheduleDTO schedule = scheduleService.findById(scheduleId, memberCode);
        log.info("[ScheduleController](findById) schedule : {} ", schedule);

        MemberDTO memberDTO = employeeService.selectMemberInfo(memberCode);

        log.info("[ScheduleController](findById) memberDTO : {} ", memberDTO);

        boolean compare = schedule.getCalendar().getMemberCode().equals(memberCode)
                || schedule.getCalendar().getDepartmentCode().equals(memberDTO.getDepartment().getDeptCode());

        ExpendsProps expendsProps = ExpendsProps.builder()
                .compare(compare)
                .build();

        return ResponseEntity.ok()
                    .body(ExpendsResponseDTO.expendsResponseBuilder()
                            .status(HttpStatus.OK)
                            .expendsProps(expendsProps)
                            .message("success")
                            .data(schedule)
                            .build());
    }


    @PatchMapping("/update") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateById(@RequestBody ScheduleDTO scheduleDTO,
                                                  @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();
        log.info("[ScheduleController](updateById) scheduleDTO : {} ", scheduleDTO);
        scheduleService.updateById(scheduleDTO, memberCode);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정되었습니다.")
                        .build());
    }

    @DeleteMapping("/delete/{scheduleId}") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int scheduleId,
                                                  @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();
        log.info("[ScheduleController](deleteById) scheduleId : {} ", scheduleId);

        scheduleService.deleteById(scheduleId, memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 삭제 되었습니다.")
                        .build());
    }

    @PostMapping("/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO, @AuthenticationPrincipal MemberDTO member){
        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();
        log.info("[ScheduleController](insertSchedule) scheduleDTO : {} ", scheduleDTO);

        scheduleService.insertSchedule(scheduleDTO, memberCode);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정 되었습니다.")
                        .build());
    }

    @GetMapping("/reminder")
    public ResponseEntity<ResponseDTO> reminder(@AuthenticationPrincipal MemberDTO member){
        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(scheduleService.reminder(memberCode))
                        .build());
    }
}
