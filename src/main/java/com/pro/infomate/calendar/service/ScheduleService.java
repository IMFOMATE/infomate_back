package com.pro.infomate.calendar.service;

import com.prev.clndr.dto.CalendarDTO;
import com.prev.clndr.dto.ScheduleDTO;
import com.prev.clndr.entity.Calendar;
import com.prev.clndr.entity.Participant;
import com.prev.clndr.entity.Schedule;
import com.prev.clndr.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;



    public List<ScheduleDTO> findAllScheduleByCalendarId(Integer calendarId) {
        return scheduleRepository.findAllByRefCalendarId(calendarId)
                .stream().map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    public ScheduleDTO findById(Integer scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        return modelMapper.map(schedule, ScheduleDTO.class);
    }

    public void updateById(Integer scheduleId, ScheduleDTO schedule) {

        Schedule entitySchedule = scheduleRepository.findById(scheduleId).get();
        entitySchedule.setSubject(schedule.getSubject());
        entitySchedule.setStartDate(schedule.getStartDate());
        entitySchedule.setEndDate(schedule.getEndDate());
        entitySchedule.setContent(schedule.getContent());
        entitySchedule.setAddress(schedule.getAddress());

        entitySchedule.setParticipantList(
                schedule.getParticipantList()
                        .stream()
                        .map(participant -> modelMapper.map(participant, Participant.class))
                        .collect(Collectors.toList()));

        entitySchedule.setCorpSchdl(schedule.getCorpSchdl());
        entitySchedule.setRepeat(schedule.getRepeat());
        entitySchedule.setImportant(schedule.getImportant());
        entitySchedule.setAllDay(schedule.getAllDay());
    }

    public void deleteById(Integer scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public List<ScheduleDTO> findScheduleSearch(String keyword, Integer userId) {
        List<Schedule> scheduleList = scheduleRepository.findScheduleSearch(keyword, userId);
        return scheduleList.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }
}
