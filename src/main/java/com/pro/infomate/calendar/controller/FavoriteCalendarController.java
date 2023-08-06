package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.service.CalendarService;
import com.pro.infomate.calendar.service.FavoriteCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calendar/favorite")
@RequiredArgsConstructor
@Slf4j
public class FavoriteCalendarController {

    private final CalendarService calendarService;

    private final FavoriteCalendarService favoriteCalendarService;

    @GetMapping("/followerList/")
    public ResponseEntity<ResponseDTO> findAllByFollwerList(Integer favoriteId){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(favoriteCalendarService.findAllByFavoriteCalendar(favoriteId))
                        .build());
    }

    @PatchMapping("/follower")
    public ResponseEntity<ResponseDTO> updateApprovalStatus(@RequestBody FavoriteCalendarDTO favoriteCalendarDTO){
        favoriteCalendarService.updateApprovalStatusById(favoriteCalendarDTO);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    @GetMapping("/followCalendar/{userId}")
    public ResponseEntity<ResponseDTO> findAllByUserId(@PathVariable Integer userId){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder().status(HttpStatus.OK.value())
                        .message("success")
                        .data(favoriteCalendarService.findAllByUserId(userId))
                        .build());
    }

    // test success
    @PostMapping
    public ResponseEntity<ResponseDTO> saveFollowCalendar(@RequestBody FavoriteCalendarDTO favoriteCalendarDTO){
        log.info("[FavoriteCalendarController](saveFollowCalendar) favoriteCalendarDTO : {}", favoriteCalendarDTO);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(favoriteCalendarService.saveFollowCalendar(favoriteCalendarDTO))
                        .build());
    }


    // test success
    @DeleteMapping("/DeleteFollowCalendar/{favoriteId}")
    public ResponseEntity<ResponseDTO> deleteFollowCalendar(@PathVariable Integer favoriteId){
        favoriteCalendarService.deleteFavoriteCalendarById(favoriteId);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }


}
