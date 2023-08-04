package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.ApprovalStatus;
import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import com.pro.infomate.calendar.entity.Schedule;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final FavotriteCalendarRepository favotriteCalendarRepository;
    private final ModelMapper modelMapper;

    public List<CalendarDTO> findAll(int memberId) {
        log.info("[CalendarService](findAll) memberId : {} ",memberId);
        List<Calendar> calendarList = calendarRepository.findByMemberCode(memberId);
        log.info("[CalendarService](findAll) calendarList : {} ",calendarList);
        List<CalendarDTO> calendarDTOList = calendarList.stream().map(calendar -> modelMapper.map(calendar, CalendarDTO.class)).collect(Collectors.toList());
        log.info("[CalendarService](findAll) calendarDTOList : {} ",calendarDTOList);

        return calendarDTOList;
    }

    public CalendarDTO findById(Integer calendarId) {
        log.info("[CalendarService](findById) calendarId : {} ",calendarId);

        Calendar calendar = calendarRepository.findById(calendarId).get();
        log.info("[CalendarService](findById) calendar : {} ",calendar);

        List<Schedule> scheduleList = calendar.getSchedule();
        log.info("[CalendarService](findById) scheduleList : {} ",scheduleList);

        CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
        log.info("[CalendarService](findById) calendarDTO : {} ",calendarDTO);

        calendarDTO.setRefScheduleList(scheduleList.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList()));

        return calendarDTO;
    }

    @Transactional
    public void saveByCalendar(CalendarDTO calendar) {
        Calendar entityCalendar = modelMapper.map(calendar, Calendar.class);

        calendarRepository.save(entityCalendar);
    }

    @Transactional
    public void updateById(Integer calendarId, CalendarDTO calendar) {
        log.info("[CalendarService](updateById) calendar : {}",calendar);
        Calendar entityCalendar = calendarRepository.findById(calendarId).get();
        log.info("[CalendarService](updateById) entityCalendar : {}",entityCalendar);
        entityCalendar.setName(calendar.getName());
        entityCalendar.setOpenStatus(calendar.getOpenStatus());
        entityCalendar.setLabelColor(calendar.getLabelColor());
        entityCalendar.setIndexNo(calendar.getIndexNo());
    }

    @Transactional
    public void updateDefaultCalender(Integer calendarId, Integer userId){
        Calendar prevDefaultCalendar = calendarRepository.findByMemberCodeAndDefaultCalendar(userId, true);
        prevDefaultCalendar.setDefaultCalendar(false);

        Calendar afterDefaultCalendar = calendarRepository.findById(calendarId).get();
        afterDefaultCalendar.setDefaultCalendar(true);
    }

    @Transactional
    public void deleteById(Integer calendarId) {
        calendarRepository.deleteById(calendarId);
    }


    public List<CalendarDTO> openCalendarList(Integer userId) {
//        List<Calendar> calendarList = calendarRepository.findByOpenCalendar(userId);
        List<Calendar> calendarList = calendarRepository.findByMemberCodeNotAndOpenStatus(userId, true);
        log.info("[CalendarService](updateById) calendarList : {}",calendarList);
        return calendarList.stream()
                .map(calendar -> {
                    List<ScheduleDTO> scheduleDTOList =
                            calendar.getSchedule().stream()
                            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                             .collect(Collectors.toList());

                   CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
                   calendarDTO.setRefScheduleList(scheduleDTOList);
                   return calendarDTO;
                })
                .collect(Collectors.toList());
    }
}
