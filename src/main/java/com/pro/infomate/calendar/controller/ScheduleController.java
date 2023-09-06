package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.service.ScheduleService;
import com.pro.infomate.common.ResponseDTO;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/dayCount/{memberCode}")
    public ResponseEntity<ResponseDTO> dayPerCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDay,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDay,
            @PathVariable int memberCode){

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

    @GetMapping("/{memberCode}/{scheduleId}") // api 연동 확인
    public ResponseEntity<ExpendsResponseDTO> findById(@PathVariable int scheduleId, @PathVariable int memberCode) {
        log.info("[ScheduleController](findById) scheduleId : {} ", scheduleId);

        ScheduleDTO schedule = scheduleService.findById(scheduleId, memberCode);
        log.info("[ScheduleController](findById) schedule : {} ", schedule);

        MemberDTO memberDTO = employeeService.selectMemberInfo(memberCode);

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


    @PatchMapping("/update/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateById(@PathVariable int memberCode, @RequestBody ScheduleDTO scheduleDTO){
        log.info("[ScheduleController](updateById) scheduleDTO : {} ", scheduleDTO);
        scheduleService.updateById(scheduleDTO, memberCode);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정되었습니다.")
                        .build());
    }

    @DeleteMapping("/delete/{scheduleId}/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int scheduleId, @PathVariable int memberCode){
        log.info("[ScheduleController](deleteById) scheduleId : {} ", scheduleId);

        scheduleService.deleteById(scheduleId, memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 삭제 되었습니다.")
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

    @PostMapping("/regist/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleDTO scheduleDTO, @PathVariable int memberCode){
        log.info("[ScheduleController](insertSchedule) scheduleDTO : {} ", scheduleDTO);

//        serverApiService.scheduleInsertApi(scheduleDTO);
        scheduleService.insertSchedule(scheduleDTO, memberCode);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 수정 되었습니다.")
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
