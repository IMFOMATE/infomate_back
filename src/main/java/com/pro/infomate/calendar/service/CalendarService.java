package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final FavotriteCalendarRepository favotriteCalendarRepository;
    private final ModelMapper modelMapper;

    public List<CalendarDTO> findAll() {
        return calendarRepository.findAll()
                .stream().map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }

    public CalendarDTO findById(Integer calendarId) {
        return modelMapper.map(calendarRepository.findById(calendarId), CalendarDTO.class);
    }


    public List<FavoriteCalendarDTO> findAllByFavoriteCalendar(Integer favoriteId){
        return favotriteCalendarRepository.findAllByRefCalendarId(favoriteId)
                .stream().map(favoriteCalendar -> modelMapper.map(favoriteCalendar, FavoriteCalendarDTO.class))
                .collect(Collectors.toList());
    }

    public List<FavoriteCalendarDTO> findAllByUserId(Integer userId) {
        return favotriteCalendarRepository.findAllByUserId(userId);
    }

    @Transactional
    public void saveByCalendar(CalendarDTO calendar) {
        Calendar entityCalendar = modelMapper.map(calendar, Calendar.class);

        calendarRepository.save(entityCalendar);
    }

    @Transactional
    public void updateById(Integer calendarId, CalendarDTO calendar) {
        Calendar entityCalendar = calendarRepository.findById(calendarId).get();
        entityCalendar.setName(calendar.getName());
        entityCalendar.setOpenStatus(calendar.getOpenStatus());
        entityCalendar.setLabelColor(calendar.getLabelColor());
        entityCalendar.setIndex(calendar.getIndex());
    }

    @Transactional
    public void updateDefaultCalender(Integer calendarId, Integer userId){
        Calendar prevDefaultCalendar = calendarRepository.findByUserIdAndDefaultCalendar(userId, true);
        prevDefaultCalendar.setDefaultCalendar(false);

        Calendar afterDefaultCalendar = calendarRepository.findById(calendarId).get();
        afterDefaultCalendar.setDefaultCalendar(true);
    }

    @Transactional
    public void deleteById(Integer calendarId) {
        calendarRepository.deleteById(calendarId);
    }

    public void updateApprovalStatusById(Integer favoriteId, ApprovalStatus status) {
        FavoriteCalendar favoriteCalendar = favotriteCalendarRepository.findById(favoriteId).get();
        favoriteCalendar.setApprovalStatus(status);

    }

    @Transactional
    public void deleteFavoriteCalendarById(Integer favoriteId) {
        favotriteCalendarRepository.deleteById(favoriteId);
    }

    public List<CalendarDTO> openCalendarList(Integer userId) {
//        List<Calendar> calendarList = calendarRepository.findByOpenCalendar(userId);
        List<Calendar> calendarList = calendarRepository.findByUserIdNotAndOpenStatus(userId, true);
        return calendarList.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .collect(Collectors.toList());
    }
}
