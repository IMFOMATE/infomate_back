package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import com.pro.infomate.exception.AlreadyRequstException;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteCalendarService {

    private final FavotriteCalendarRepository favotriteCalendarRepository;

    private final CalendarRepository calendarRepository;

    private final ModelMapper modelMapper;

    public Page<FavoriteCalendarDTO> findAllByFavoriteCalendar(Integer memberCode, Pageable pageable){

        Page<FavoriteCalendar> favoriteCalendarList = favotriteCalendarRepository.findByCalendarByMemberCode(memberCode, pageable);

        log.info("[FavoriteCalendarService](findAllByFavoriteCalendar) calendarList : {}",favoriteCalendarList);

        Page<FavoriteCalendarDTO> favoriteCalendarDTOList = favoriteCalendarList
                .map(favoriteCalendar -> modelMapper.map(favoriteCalendar, FavoriteCalendarDTO.class))
                .map(favoriteCalendarDTO -> {

                    CalendarDTO calendarDTO = favoriteCalendarDTO.getCalendar();
                    calendarDTO.setFavoriteCalendar(null);
                    calendarDTO.setScheduleList(null);


                    MemberDTO memberDTO = new MemberDTO();
                    memberDTO.setMemberName(favoriteCalendarDTO.getMember().getMemberName());
                    memberDTO.setMemberNo(favoriteCalendarDTO.getMember().getMemberNo());
                    memberDTO.setRank(favoriteCalendarDTO.getCalendar().getMember().getRank());
                    calendarDTO.setMember(memberDTO);

                    favoriteCalendarDTO.setCalendar(calendarDTO);
                    return favoriteCalendarDTO;
                });

        log.info("[FavoriteCalendarService](findAllByFavoriteCalendar) favoriteCalendarDTOList : {}",favoriteCalendarDTOList);

        return favoriteCalendarDTOList;

    }

    public Page<FavoriteCalendarDTO> findAllByMemberCode(Integer memberCode, Pageable pageable) {
        Page<FavoriteCalendar> favoriteCalendars = favotriteCalendarRepository.findAllByMemberCode(memberCode, pageable);
        log.info("[FavoriteCalendarService](findAllByMemberCode) favoriteCalendars : {}", favoriteCalendars);

        if(favoriteCalendars == null || favoriteCalendars.getSize() == 0 ) throw new NotFindDataException("데이터가 없습니다.");


        return favoriteCalendars
                .map(favoriteCalendar -> modelMapper.map(favoriteCalendar,FavoriteCalendarDTO.class))
                .map(favoriteCalendarDTO -> {
                    favoriteCalendarDTO.getCalendar().setFavoriteCalendar(null);

                    MemberDTO calMemberDTO = new MemberDTO();
                    calMemberDTO.setMemberCode(favoriteCalendarDTO.getCalendar().getMember().getMemberCode());
                    calMemberDTO.setMemberName(favoriteCalendarDTO.getCalendar().getMember().getMemberName());
                    calMemberDTO.setRank(favoriteCalendarDTO.getCalendar().getMember().getRank());

                    log.info("[FavoriteCalendarService](findAllByMemberCode) calMemberDTO : {}", calMemberDTO);

                    favoriteCalendarDTO.getCalendar().setMember(calMemberDTO);
                    favoriteCalendarDTO.setMember(null);
                    favoriteCalendarDTO.getCalendar().setScheduleList(null);

                    return favoriteCalendarDTO;
                });
    }

    @Transactional
    public void updateApprovalStatusById(List<FavoriteCalendarDTO> favoriteList) {

        favoriteList.stream().forEach(favorite -> {
            FavoriteCalendar favoriteCalendar = favotriteCalendarRepository
                    .findById(favorite.getId())
                    .orElseThrow(() -> new NotFindDataException("캘린더를 찾을 수 없습니다"));

            favoriteCalendar.setApprovalStatus(favorite.getApprovalStatus());
        });
    }

    @Transactional
    public void deleteFavoriteCalendarById(List<Integer> favoriteId) {
        favotriteCalendarRepository.deleteAllById(favoriteId);
    }

    @Transactional
    public void saveFollowCalendar(List<FavoriteCalendarDTO> favoriteCalendarDTO) {

        favoriteCalendarDTO.forEach(favoriteCalendarDTO1 -> {
            Optional<FavoriteCalendar> favoriteCalendar = favotriteCalendarRepository
                    .findByMemberCodeAndRefCalendar(favoriteCalendarDTO1.getMemberCode(), favoriteCalendarDTO1.getRefCalendar());
            if(favoriteCalendar.isPresent()) throw new AlreadyRequstException("이미 요청한 캘린더가 포함되어 있습니다.");

        });

        favoriteCalendarDTO.stream()
                .map(favoriteCalendar -> modelMapper.map(favoriteCalendar, FavoriteCalendar.class))
                .forEach(favoriteCalendar -> {
                    Calendar calendar = calendarRepository
                            .findById(favoriteCalendar.getRefCalendar())
                            .orElseThrow(() -> new NotFindDataException("캘린더를 찾을 수 없습니다"));

                    if(!calendar.getOpenStatus()) throw new NotFindDataException("캘린더를 찾을 수 없거나 비공개된 캘린더 입니다");
                    favoriteCalendar.setApprovalStatus(ApprovalStatus.WAIT);
                    favoriteCalendar.setRequestDate(LocalDateTime.now());

                    favotriteCalendarRepository.save(favoriteCalendar);
                });
    }
}
