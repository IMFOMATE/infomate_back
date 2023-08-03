package com.pro.infomate.calendar.controller;


import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> findAll(){
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.findAll())
                        .build());
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<ResponseDTO> findById(@PathVariable Integer calendarId){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.findById(calendarId))
                        .build());
    }

    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> saveByCalendar(CalendarDTO calendar){
        calendarService.saveByCalendar(calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @PatchMapping("/update/{calendarId}")
    public ResponseEntity<ResponseDTO> updateByCalendar(@PathVariable Integer calendarId, CalendarDTO calendar){
        calendarService.updateById(calendarId, calendar);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }
    @PutMapping("/updateDafault/{calendarId}")
    public ResponseEntity<ResponseDTO> updateDefaultByCalendar(@PathVariable Integer calendarId){
        calendarService.updateDefaultCalender(calendarId, null); // userId 수정 요망
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @DeleteMapping("/delete/{calendarId}")
    public ResponseEntity<ResponseDTO> deleteByCalendar(@PathVariable Integer calendarId){
        calendarService.deleteById(calendarId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @GetMapping("/followerList")
    public ResponseEntity<ResponseDTO> findAllByFollwerList(Integer favoriteId){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.findAllByFavoriteCalendar(favoriteId))
                        .build());
    }

    @PatchMapping("/follower/{favoriteId}")
    public ResponseEntity<ResponseDTO> updateApprovalStatus(@PathVariable Integer favoriteId, ApprovalStatus status){
        calendarService.updateApprovalStatusById(favoriteId, status);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @GetMapping("/followCalendar")
    public ResponseEntity<ResponseDTO> findAllByUserId(Integer userId){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder().status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.findAllByUserId(userId))
                        .build());
    }

    @DeleteMapping("/DeleteFollowCalendar/{favoriteId}")
    public ResponseEntity<ResponseDTO> deleteFollowCalendar(@PathVariable Integer favoriteId){
        calendarService.deleteFavoriteCalendarById(favoriteId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @GetMapping("/openFollowerCalendar")
    public ResponseEntity<ResponseDTO> findOpenCalendarList(){
        Integer userId = null;
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(calendarService.openCalendarList(userId))
                        .build());
    }
}
