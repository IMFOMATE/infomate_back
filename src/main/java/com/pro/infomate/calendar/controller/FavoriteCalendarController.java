package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.service.FavoriteCalendarService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.ExpendsResponseDTO;
import com.pro.infomate.common.PageDTO;
import com.pro.infomate.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/followerList/{memberCode}") // api 연동 확인
    public ResponseEntity<ResponseDTO> findAllByFollowerList(@PathVariable Integer memberCode, Pageable pageable){

        pageable = PageRequest.of(
                pageable.getPageNumber() - 1 < 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );

        Page<FavoriteCalendarDTO> favoriteCalendarDTOPage = favoriteCalendarService.findAllByFavoriteCalendar(memberCode, pageable);

        return ResponseEntity.ok()
                .body(ExpendsResponseDTO.expendsResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(favoriteCalendarDTOPage.getContent())
                        .pageDTO(new PageDTO(new Criteria(pageable.getPageNumber(),pageable.getPageSize()), favoriteCalendarDTOPage.getTotalPages()))
                        .build());
    }

    @PatchMapping("/follower") // api 연동 확인
    public ResponseEntity<ResponseDTO> updateApprovalStatus(@RequestBody List<FavoriteCalendarDTO> favoriteList){
        log.info("[FavoriteCalendarController](updateApprovalStatus) favoriteIdList : {}", favoriteList);

        favoriteCalendarService.updateApprovalStatusById(favoriteList);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 승인됐습니다.")
                        .build());
    }


    @GetMapping("/follow/{memberCode}") // api 연동 확인
//    public ResponseEntity<ResponseDTO> findAllByMemberCode(@PathVariable Integer memberCode, Pageable pageable){
    public ResponseEntity<ExpendsResponseDTO> findAllByMemberCode(@PathVariable Integer memberCode, Pageable pageable){
        log.info("[FavoriteCalendarController](findAllByMemberCode) memberCode : {}", memberCode);

        log.info("[FavoriteCalendarController](findAllByMemberCode) pageable : {}", pageable);

        pageable = PageRequest.of(pageable.getPageNumber() -1 < 0? 0 : pageable.getPageNumber() - 1,pageable.getPageSize(),pageable.getSort());

        Page<FavoriteCalendarDTO> favoriteCalendarDTOPage = favoriteCalendarService.findAllByMemberCode(memberCode, pageable);

//        return ResponseEntity.ok()
//                .body(ResponseDTO.builder().status(HttpStatus.OK)
//                        .message("success")
//                        .data(favoriteCalendarService.findAllByMemberCode(memberCode))
//                        .build());

        return ResponseEntity.ok()
                .body(ExpendsResponseDTO.expendsResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("success")
                        .data(favoriteCalendarDTOPage.getContent())
                        .pageDTO(new PageDTO(new Criteria(
                                pageable.getPageNumber(), pageable.getPageSize()),favoriteCalendarDTOPage.getTotalPages()
                        ))
                        .build());
    }


    @PostMapping("/follwer/regist") // api 연동 확인
    public ResponseEntity<ResponseDTO> saveFollowCalendar(@RequestBody List<FavoriteCalendarDTO> favoriteCalendarDTO){
        log.info("[FavoriteCalendarController](saveFollowCalendar) favoriteCalendarDTO : {}", favoriteCalendarDTO);
        favoriteCalendarService.saveFollowCalendar(favoriteCalendarDTO);
        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("성공적으로 등록되었습니다.")
                        .build());
    }



    @DeleteMapping("/DeleteFollowCalendar") // api 연동 확인
    public ResponseEntity<ResponseDTO> deleteFollowCalendar(@RequestBody List<Integer> favoriteId){
        log.info("[FavoriteCalendarController](saveFollowCalendar) deleteFollowCalendar : {}", favoriteId);

        favoriteCalendarService.deleteFavoriteCalendarById(favoriteId);

        return ResponseEntity.ok()
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .message("정상적으로 삭제 되었습니다.")
                        .build());
    }


}
