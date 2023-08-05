package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteCalendarService {

    private final FavotriteCalendarRepository favotriteCalendarRepository;

    private final ModelMapper modelMapper;

    public List<FavoriteCalendarDTO> findAllByFavoriteCalendar(Integer favoriteId){
        return favotriteCalendarRepository.findAllByRefCalendar(favoriteId)
                .stream().map(favoriteCalendar -> modelMapper.map(favoriteCalendar, FavoriteCalendarDTO.class))
                .collect(Collectors.toList());
    }

    public List<FavoriteCalendarDTO> findAllByUserId(Integer userId) {
        return favotriteCalendarRepository.findAllByMemberCode(userId);
    }

    public void updateApprovalStatusById(Integer favoriteId, ApprovalStatus status) {
        FavoriteCalendar favoriteCalendar = favotriteCalendarRepository.findById(favoriteId).get();
        favoriteCalendar.setApprovalStatus(status);

    }

    @Transactional
    public void deleteFavoriteCalendarById(Integer favoriteId) {
        favotriteCalendarRepository.deleteById(favoriteId);
    }

}
