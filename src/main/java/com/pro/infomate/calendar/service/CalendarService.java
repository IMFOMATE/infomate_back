package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.CalendarSummary;
import com.pro.infomate.calendar.entity.Schedule;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import com.pro.infomate.exception.NotFindDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final FavotriteCalendarRepository favotriteCalendarRepository;
    private final ModelMapper modelMapper;


    public List<CalendarDTO> findAll(int memberId) {
        log.info("[CalendarService](findAll) memberId : {} ",memberId);

        List<Calendar> calendarList = calendarRepository.findByMemberCode(memberId);
        log.info("[CalendarService](findAll) calendarList : {} ",calendarList);

        List<CalendarDTO> calendarDTOList = calendarList.stream()
                .map(calendar -> modelMapper.map(calendar,CalendarDTO.class))
                .map(calendarDTO -> {
                    calendarDTO.setFavoriteCalendar(null);
                    log.info("[CalendarService](findAll) calendarDTO : {} ",calendarDTO);
                    return calendarDTO;
                }).collect(Collectors.toList());

        log.info("[CalendarService](findAll) calendarDTOList : {} ",calendarDTOList);

        return calendarDTOList;
    }

    public CalendarDTO findById(Integer calendarId) {
        log.info("[CalendarService](findById) calendarId : {} ",calendarId);

        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
        log.info("[CalendarService](findById) calendar : {} ",calendar);

        if (calendar.isEmpty()) throw new NotFindDataException("데이터를 찾을 수 없습니다.");

        List<Schedule> scheduleList = calendar.get().getSchedule();
        log.info("[CalendarService](findById) scheduleList : {} ",scheduleList);

        CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
        log.info("[CalendarService](findById) calendarDTO : {} ",calendarDTO);

        calendarDTO.setRefScheduleList(scheduleList.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList()));

        log.info("[CalendarService](findById) calendarDTOReMapping : {} ",calendarDTO);

        return calendarDTO;
    }

    @Transactional
    public void saveByCalendar(CalendarDTO calendar) {
        if(calendar.getDefaultCalendar()){
            Optional<Calendar> defaultCalendar = calendarRepository.findByMemberCodeAndDefaultCalendar(calendar.getMemberCode(), true);

            if(defaultCalendar.isEmpty()) throw new NotFindDataException("기본 캘린더를 찾을 수 없습니다");

            log.info("[CalendarService](saveByCalendar) DefaultCalendar : {}", calendar);

            defaultCalendar.get().setDefaultCalendar(false);

        }

        Calendar entityCalendar = modelMapper.map(calendar, Calendar.class);
        log.info("[CalendarService](saveByCalendar) entityCalendar : {}", entityCalendar);

        calendarRepository.save(entityCalendar);
    }

    @Transactional
    public void updateById(CalendarDTO calendar) {
        log.info("[CalendarService](updateById) calendar : {}", calendar);

        Optional<Calendar> entityCalendar = calendarRepository.findById(calendar.getId());
        if(entityCalendar.isEmpty()) throw new NotFindDataException("수정할 캘린더를 찾을 수 없습니다.");

        log.info("[CalendarService](updateById) entityCalendar : {}",entityCalendar.get());

        entityCalendar.get().setName(calendar.getName());
        entityCalendar.get().setOpenStatus(calendar.getOpenStatus());
        entityCalendar.get().setLabelColor(calendar.getLabelColor());
        entityCalendar.get().setIndexNo(calendar.getIndexNo());
        entityCalendar.get().setDepartmentCode(calendar.getDepartmentCode());
    }

    @Transactional
    public void updateDefaultCalender(CalendarDTO calendarDTO){
        Optional<Calendar> prevDefaultCalendar = calendarRepository.findByMemberCodeAndDefaultCalendar(calendarDTO.getMemberCode(), true);

        if(prevDefaultCalendar.isEmpty()) throw new NotFindDataException("기본 캘린더를 찾을 수 없습니다.");

        log.info("[CalendarService](updateDefaultCalender) prevDefaultCalendar : {}", prevDefaultCalendar.get());

        prevDefaultCalendar.get().setDefaultCalendar(false);


        Optional<Calendar> afterDefaultCalendar = calendarRepository.findById(calendarDTO.getId());
        if(afterDefaultCalendar.isEmpty()) throw new NotFindDataException("수정할 캘린더를 찾을 수 없습니다.");

        log.info("[CalendarService](updateDefaultCalender) afterDefaultCalendar : {}", afterDefaultCalendar.get());

        afterDefaultCalendar.get().setDefaultCalendar(true);

    }

    @Transactional
    public void deleteById(List<Integer> calendarId) {

        log.info("[CalendarService](deleteById) scheduleDTOList : {}",calendarId);

        calendarRepository.deleteAllById(calendarId);
    }


    public List<CalendarDTO> openCalendarList() {

        List<Calendar> calendarList = calendarRepository.findByDepartmentCodeAndOpenStatus(null ,true);
        log.info("[CalendarService](openCalendarList) calendarList : {}",calendarList);

        if(calendarList.size() == 0 ) return null;

        return calendarList.stream()
                .map(calendar -> {
                    List<ScheduleDTO> scheduleDTOList =
                            calendar.getSchedule().stream()
                            .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                             .collect(Collectors.toList());

                    log.info("[CalendarService](openCalendarList) scheduleDTOList : {}",scheduleDTOList);

                   CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
                   calendarDTO.setRefScheduleList(scheduleDTOList);

                    log.info("[CalendarService](openCalendarList) calendarDTO : {}",calendarDTO);
                   return calendarDTO;
                })
                .collect(Collectors.toList());
    }

    public List<CalendarSummaryDTO> findSummaryCalendar(int memberCode) {

        log.info("[CalendarService](findSummaryCalendar) memberCode : {}", memberCode);

        // 월의 일별 일정 갯수 표시
        List<CalendarSummaryDTO> scheduleList = calendarRepository.findAllByDaysCount(memberCode, LocalDate.now())
                .stream().map(objects -> CalendarSummaryDTO.builder()
                        .amount((Long) objects[0])
                        .day(((LocalDateTime) objects[1]).toLocalDate())
                        .build())
                .collect(Collectors.toList());

        log.info("[CalendarService](findSummaryCalendar) scheduleList : {}", scheduleList);

        return scheduleList;
    }

    public List<CalendarDTO> myCalendarList(int memberCode) {

        List<Calendar> calendarList =
                calendarRepository.findAllByMemberCodeAndDepartmentCode(memberCode, null, Sort.by(Sort.Direction.ASC, "indexNo"));

        return calendarList.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .map(calendarDTO -> {
                    calendarDTO.setFavoriteCalendar(null);
                    calendarDTO.setRefScheduleList(null);
                    return calendarDTO;
                }).collect(Collectors.toList());

    }
}
