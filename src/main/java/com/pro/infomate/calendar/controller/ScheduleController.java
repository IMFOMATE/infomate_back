package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.DayPerCountDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.service.ScheduleService;
import com.pro.infomate.common.ExpendsProps;
import com.pro.infomate.common.ExpendsResponseDTO;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(tags = "Schedule", description = "기간별 일별 일정 갯수 ", summary = "일별 일정 갯수 표시 API")
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

    @Operation(tags = "Schedule", description = "일정 상세 정보 호출", summary = "일정 정보 호출 API")
    @GetMapping("/{scheduleId}") // api 연동 확인
    public ResponseEntity<ExpendsResponseDTO> findById(@PathVariable int scheduleId, @AuthenticationPrincipal MemberDTO member) {
        int department = member.getDepartment().getDeptCode();
        int memberCode = member.getMemberCode();
        log.info("[ScheduleController](findById) scheduleId : {} ", scheduleId);

        ScheduleDTO schedule = scheduleService.findById(scheduleId, memberCode);
        log.info("[ScheduleController](findById) schedule : {} ", schedule);

        MemberDTO memberDTO = employeeService.selectMemberInfo(memberCode);

        log.info("[ScheduleController](findById) memberDTO : {} ", memberDTO);
        log.info("[ScheduleController](findById) memberDTO.getDepartment().getDeptCode() : {} ", memberDTO.getDepartment().getDeptCode());

        boolean compare = false;

        if(schedule.getCalendar().getMemberCode().equals(memberCode)
                && schedule.getCalendar().getDepartmentCode() == null)
            compare = true;
        else if(schedule.getCalendar().getDepartmentCode() != null
                && schedule.getCalendar().getDepartmentCode().equals(memberDTO.getDepartment().getDeptCode())){
            compare = true;
        } else{
            compare = false;
        }

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

    @Operation(tags = "Schedule", description = "일정 수정 API", summary = "일정 수정 API")
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

    @Operation(tags = "Schedule", description = "일정 삭제", summary = "일정 삭제 API")
    @DeleteMapping("/delete/{scheduleId}") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int scheduleId,
                                                  @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();
        int deptCode = member.getDepartment().getDeptCode();
        log.info("[ScheduleController](deleteById) scheduleId : {} ", scheduleId);

        scheduleService.deleteById(scheduleId, memberCode, deptCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 삭제 되었습니다.")
                        .build());
    }

    @Operation(tags = "Schedule", description = "일정 등록", summary = "일정 등록 API")
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

    @Operation(tags = "Schedule", description = "대시보드에서 사용되는 팀일정 api", summary = "대시보드용 팀일정 표시 API")
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
