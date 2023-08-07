package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import com.pro.infomate.exception.NotFindDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<FavoriteCalendarDTO> findAllByFavoriteCalendar(Integer favoriteId){
        return favotriteCalendarRepository.findAllByRefCalendar(favoriteId)
                .stream().map(favoriteCalendar -> modelMapper.map(favoriteCalendar, FavoriteCalendarDTO.class))
                .collect(Collectors.toList());
    }

    public List<FavoriteCalendarDTO> findAllByUserId(Integer userId) {
        return favotriteCalendarRepository.findAllByMemberCode(userId);
    }

    @Transactional
    public void updateApprovalStatusById(FavoriteCalendarDTO favoriteCalendarDTO) {
        Optional<FavoriteCalendar> favoriteCalendar = favotriteCalendarRepository.findById(favoriteCalendarDTO.getId());

        if(favoriteCalendar.isEmpty()) throw new NotFindDataException("캘린더를 찾을 수 없습니다.");

        favoriteCalendar.get().setApprovalStatus(favoriteCalendarDTO.getApprovalStatus());

    }

    @Transactional
    public void deleteFavoriteCalendarById(Integer favoriteId) {
        favotriteCalendarRepository.deleteById(favoriteId);
    }

    @Transactional
    public FavoriteCalendarDTO saveFollowCalendar(FavoriteCalendarDTO favoriteCalendarDTO) {

        Optional<Calendar> calendar = calendarRepository.findById(favoriteCalendarDTO.getRefCalendar());

        if(calendar.isEmpty() && !calendar.get().getOpenStatus()) throw new NotFindDataException("캘린더를 찾을 수 없거나 비공개된 캘린더 입니다");

        FavoriteCalendar favoriteCalendar = modelMapper.map(favoriteCalendarDTO, FavoriteCalendar.class);

        return modelMapper.map(favotriteCalendarRepository.save(favoriteCalendar), FavoriteCalendarDTO.class);

    }
}
