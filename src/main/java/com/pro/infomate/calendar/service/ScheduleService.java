package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.Participant;
import com.pro.infomate.calendar.entity.Schedule;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;
    private final CalendarRepository calendarRepository;

    public List<ScheduleDTO> findAllScheduleByCalendarId(Integer calendarId) {
//        return scheduleRepository.findAllByRefCalendarId(calendarId)
        return scheduleRepository.findAll()
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    public ScheduleDTO findById(Integer scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        return modelMapper.map(schedule, ScheduleDTO.class);
    }

    public void updateById(ScheduleDTO schedule) {
        log.info("[ScheduleService](updateById) schedule : {}",schedule);

        Schedule entitySchedule = scheduleRepository.findById(schedule.getId()).get();
        log.info("[ScheduleService](updateById) entitySchedule : {}",entitySchedule);

        entitySchedule.setTitle(schedule.getTitle());
        entitySchedule.setStartDate(schedule.getStartDate());
        entitySchedule.setEndDate(schedule.getEndDate());
        entitySchedule.setContent(schedule.getContent());
        entitySchedule.setAddress(schedule.getAddress());

//        entitySchedule.setParticipantList(
//                schedule.getParticipantList()
//                        stream()
//                        .map(participant -> modelMapper.map(participant, Participant.class))
//                        .collect(Collectors.toList()));

        entitySchedule.setCorpSchdl(schedule.getCorpSchdl());
        entitySchedule.setRepeat(schedule.getRepeat());
        entitySchedule.setImportant(schedule.getImportant());
        entitySchedule.setAllDay(schedule.getAllDay());

        scheduleRepository.save(entitySchedule);
    }

    public void deleteById(Integer scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public List<ScheduleDTO> findScheduleSearch(String keyword, Integer userId) {
//        List<Schedule> scheduleList = scheduleRepository.findScheduleSearch(keyword, userId);
        List<Schedule> scheduleList = new ArrayList<>();
        return scheduleList.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Object insertSchedule(ScheduleDTO scheduleDTO) {
        log.info("[ScheduleService](insertSchedule) scheduleDTO : {}",scheduleDTO);
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        Calendar calendar = calendarRepository.findById(scheduleDTO.getRefCalendar()).get();
//        schedule.setRefCalendar(calendar);
        log.info("[ScheduleService](insertSchedule) schedule : {}",schedule);
        return scheduleRepository.save(schedule);
    }
}
