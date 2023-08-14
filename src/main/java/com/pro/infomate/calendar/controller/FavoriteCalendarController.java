package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.common.ResponseDTO;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
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

    private final FavoriteCalendarService favoriteCalendarService;

    // test success
    @GetMapping("/followerList/{memberCode}")
    public ResponseEntity<ResponseDTO> findAllByFollwerList(@PathVariable Integer memberCode){

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .data(favoriteCalendarService.findAllByFavoriteCalendar(memberCode))
                        .build());
    }

    // test success
    @PatchMapping("/follower")
    public ResponseEntity<ResponseDTO> updateApprovalStatus(@RequestBody FavoriteCalendarDTO favoriteCalendarDTO){
        favoriteCalendarService.updateApprovalStatusById(favoriteCalendarDTO);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }

    // test success
    @GetMapping("/follow/{memberCode}")
    public ResponseEntity<ResponseDTO> findAllByMemberCode(@PathVariable Integer memberCode){
        log.info("[FavoriteCalendarController](findAllByMemberCode) memberCode : {}", memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder().status(HttpStatus.OK.value())
                        .message("success")
                        .data(favoriteCalendarService.findAllByMemberCode(memberCode))
                        .build());
    }

    // test success
    @PostMapping("/follwer/regist")
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
        log.info("[FavoriteCalendarController](saveFollowCalendar) deleteFollowCalendar : {}", favoriteId);

        favoriteCalendarService.deleteFavoriteCalendarById(favoriteId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK.value())
                        .message("success")
                        .build());
    }


}
