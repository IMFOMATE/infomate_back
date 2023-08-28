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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteCalendarService {

    private final FavotriteCalendarRepository favotriteCalendarRepository;

    private final CalendarRepository calendarRepository;

    private final ModelMapper modelMapper;

    public List<FavoriteCalendarDTO> findAllByFavoriteCalendar(Integer memberCode){

        List<FavoriteCalendar> favoriteCalendarList = favotriteCalendarRepository.findByCalendarByMemberCode(memberCode);

        log.info("[FavoriteCalendarService](findAllByFavoriteCalendar) calendarList : {}",favoriteCalendarList);

        List<FavoriteCalendarDTO> favoriteCalendarDTOList = favoriteCalendarList.stream()
                .map(favoriteCalendar -> modelMapper.map(favoriteCalendar, FavoriteCalendarDTO.class))
                .map(favoriteCalendarDTO -> {

                    CalendarDTO calendarDTO = favoriteCalendarDTO.getCalendar();
                    calendarDTO.setMember(null);
                    calendarDTO.setFavoriteCalendar(null);
                    calendarDTO.setScheduleList(null);
                    favoriteCalendarDTO.setCalendar(calendarDTO);

                    MemberDTO memberDTO = new MemberDTO();
                    memberDTO.setMemberName(favoriteCalendarDTO.getMember().getMemberName());
                    memberDTO.setMemberNo(favoriteCalendarDTO.getMember().getMemberNo());
                    favoriteCalendarDTO.setMember(memberDTO);


                    return favoriteCalendarDTO;
                })
                .collect(Collectors.toList());

        log.info("[FavoriteCalendarService](findAllByFavoriteCalendar) favoriteCalendarDTOList : {}",favoriteCalendarDTOList);

        return favoriteCalendarDTOList;

    }

    public List<FavoriteCalendarDTO> findAllByMemberCode(Integer memberCode) {
        List<FavoriteCalendar> favoriteCalendars = favotriteCalendarRepository.findAllByMemberCode(memberCode);

        return favoriteCalendars.stream()
                .map(favoriteCalendar -> modelMapper.map(favoriteCalendar,FavoriteCalendarDTO.class))
                .map(favoriteCalendarDTO -> {
                    favoriteCalendarDTO.getCalendar().setFavoriteCalendar(null);
                    favoriteCalendarDTO.getCalendar().setMember(null);
                    favoriteCalendarDTO.getCalendar().setScheduleList(null);

                    MemberDTO memberDTO = new MemberDTO();
                    memberDTO.setMemberNo(favoriteCalendarDTO.getMember().getMemberNo());
                    memberDTO.setMemberName(favoriteCalendarDTO.getMember().getMemberName());

                    favoriteCalendarDTO.setMember(memberDTO);
                    return favoriteCalendarDTO;
                }).collect(Collectors.toList());
    }

    @Transactional
    public void updateApprovalStatusById(List<FavoriteCalendarDTO> favoriteList) {

        favoriteList.stream().forEach(favorite -> {
            Optional<FavoriteCalendar> favoriteCalendar = favotriteCalendarRepository.findById(favorite.getId());

            if(favoriteCalendar.isEmpty()) throw new NotFindDataException("캘린더를 찾을 수 없습니다.");

            favoriteCalendar.get().setApprovalStatus(favorite.getApprovalStatus());
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
                    Optional<Calendar> calendar = calendarRepository.findById(favoriteCalendar.getRefCalendar());
                    if(calendar.isEmpty() || !calendar.get().getOpenStatus()) throw new NotFindDataException("캘린더를 찾을 수 없거나 비공개된 캘린더 입니다");
                    favoriteCalendar.setApprovalStatus(ApprovalStatus.WAIT);
                    favoriteCalendar.setRequestDate(LocalDateTime.now());
                    favotriteCalendarRepository.save(favoriteCalendar);
                });
    }
}
