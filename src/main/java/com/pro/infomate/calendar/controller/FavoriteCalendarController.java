package com.pro.infomate.calendar.controller;

import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.service.FavoriteCalendarService;
import com.pro.infomate.common.Criteria;
import com.pro.infomate.common.ExpendsResponseDTO;
import com.pro.infomate.common.PageDTO;
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

import java.util.List;

@RestController
@RequestMapping("/calendar/favorite")
@RequiredArgsConstructor
@Slf4j
public class FavoriteCalendarController {

    private final FavoriteCalendarService favoriteCalendarService;

    @Operation(tags = "FavCalendar", description = "사용자의 공개된 캘린더를 구독중인 사람과 캘린더의 정보를 표시", summary = "사용자의 캘린더를 구독한 사람의 정보 API")
    @GetMapping("/followerList") // api 연동 확인
    public ResponseEntity<ResponseDTO> findAllByFollowerList(Pageable pageable,
                                                             @AuthenticationPrincipal MemberDTO member){

        int memberCode = member.getMemberCode();
        log.info("[FavoriteCalendarController](findAllByFollowerList) pageable : {}", pageable);
        log.info("[FavoriteCalendarController](findAllByFollowerList) member : {}", member);

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
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

    @Operation(tags = "FavCalendar", description = "내 캘린더를 구독하고 있는 사람의 승인과 거절 삭제등 구독 상태를 변경할 수 있는 API", summary = "내 캘린더 구독 열람 가능 여부 수정 API")
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

    @Operation(tags = "FavCalendar", description = "사용자가 구독중인 캐린더 목록을 가져오는 API 거절의 제외하고 승인요청과 승인되 내역만 반화", summary = "구독중인 캘린더 목록 API")
    @GetMapping("/follow")
    public ResponseEntity<ExpendsResponseDTO> findAllByMemberCode(@AuthenticationPrincipal MemberDTO member, Pageable pageable){
        int memberCode = member.getMemberCode();
        log.info("[FavoriteCalendarController](findAllByMemberCode) memberCode : {}", memberCode);
//
        log.info("[FavoriteCalendarController](findAllByMemberCode) pageable : {}", pageable);

        pageable = PageRequest.of(pageable.getPageNumber() -1 < 0? 0 : pageable.getPageNumber() - 1,pageable.getPageSize(),pageable.getSort());

        Page<FavoriteCalendarDTO> favoriteCalendarDTOPage = favoriteCalendarService.findAllByMemberCode(memberCode, pageable);

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

    @Operation(tags = "FavCalendar", description = "공개된 캘린더 목록에서 구독을 요청", summary = "구독 요청 API")
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

    @Operation(tags = "FavCalendar", description = "구독한 캘린더 삭제 하는 API", summary = "구독한 캘린더 삭제 API")
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
