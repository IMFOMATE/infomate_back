package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.service.FavoriteCalendarService;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                        .status(HttpStatus.OK)
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
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }

    // test success
    @GetMapping("/follow/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> findAllByMemberCode(@PathVariable Integer memberCode){
        log.info("[FavoriteCalendarController](findAllByMemberCode) memberCode : {}", memberCode);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder().status(HttpStatus.OK)
                        .message("success")
                        .data(favoriteCalendarService.findAllByMemberCode(memberCode))
                        .build());
    }

    // test success
    @PostMapping("/follwer/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> saveFollowCalendar(@RequestBody List<FavoriteCalendarDTO> favoriteCalendarDTO){
        log.info("[FavoriteCalendarController](saveFollowCalendar) favoriteCalendarDTO : {}", favoriteCalendarDTO);
        favoriteCalendarService.saveFollowCalendar(favoriteCalendarDTO);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }


    // test success
    @DeleteMapping("/DeleteFollowCalendar")
    public ResponseEntity<ResponseDTO> deleteFollowCalendar(@RequestBody List<Integer> favoriteId){
        log.info("[FavoriteCalendarController](saveFollowCalendar) deleteFollowCalendar : {}", favoriteId);

        favoriteCalendarService.deleteFavoriteCalendarById(favoriteId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .build());
    }


}
