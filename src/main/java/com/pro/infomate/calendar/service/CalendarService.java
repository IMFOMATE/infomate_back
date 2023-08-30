package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.FavotriteCalendarRepository;
import com.pro.infomate.calendar.repository.ScheduleRepository;
import com.pro.infomate.exception.InvalidRequestException;
import com.pro.infomate.exception.NotEnoughDateException;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ScheduleRepository scheduleRepository;
    private final FavotriteCalendarRepository favotriteCalendarRepository;
    private final ModelMapper modelMapper;


    public List<CalendarDTO> findAll(int memberCode, int departmentCode) {
        log.info("[CalendarService](findAll) memberId : {} ",memberCode);

        List<Calendar> calendarList = calendarRepository.findByMemberCode(memberCode, departmentCode);
        log.info("[CalendarService](findAll) calendarList : {} ",calendarList);

        List<CalendarDTO> calendarDTOList = calendarList.stream()
                .map(calendar -> modelMapper.map(calendar,CalendarDTO.class))
                .map(calendarDTO -> {
                    calendarDTO.setFavoriteCalendar(null);
                    calendarDTO.setMember(null);

                    calendarDTO.setScheduleList(
                            calendarDTO.getScheduleList().stream()
                                    .map(scheduleDTO -> {
                                        scheduleDTO.setCalendar(null);
                                        scheduleDTO.setParticipantList(null);
                                        scheduleDTO.setCalendar(null);
                                        return scheduleDTO;
                                    }).collect(Collectors.toList())
                    );

                    log.info("[CalendarService](findAll) calendarDTO : {} ",calendarDTO);
                    return calendarDTO;
                }).collect(Collectors.toList());

        log.info("[CalendarService](findAll) calendarDTOList : {} ",calendarDTOList);

        return calendarDTOList;
    }

// 안씀
//    public CalendarDTO findById(Integer calendarId) {
//        log.info("[CalendarService](findById) calendarId : {} ",calendarId);
//
//        Optional<Calendar> calendar = calendarRepository.findById(calendarId);
//        log.info("[CalendarService](findById) calendar : {} ",calendar);
//
//        if (calendar.isEmpty()) throw new NotFindDataException("데이터를 찾을 수 없습니다.");
//
//        List<Schedule> scheduleList = calendar.get().getSchedule();
//        log.info("[CalendarService](findById) scheduleList : {} ",scheduleList);
//
//        CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);
//        log.info("[CalendarService](findById) calendarDTO : {} ",calendarDTO);
//
//        calendarDTO.setScheduleList(scheduleList.stream()
//                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class)).collect(Collectors.toList()));
//
//        log.info("[CalendarService](findById) calendarDTOReMapping : {} ",calendarDTO);
//
//        return calendarDTO;
//    }

    @Transactional
    public void saveByCalendar(CalendarDTO calendar) {
        if(calendar.getDefaultCalendar() != null && calendar.getDefaultCalendar()){
            Calendar defaultCalendar = calendarRepository
                    .findByMemberCodeAndDefaultCalendar(calendar.getMemberCode(), true)
                    .orElseThrow(() -> new NotFindDataException("캘린더를 찾을 수 없습니다"));

            log.info("[CalendarService](saveByCalendar) DefaultCalendar : {}", calendar);

            defaultCalendar.setDefaultCalendar(false);
        }

        Optional<Calendar> lastIndexNo = calendarRepository.findFirstByMemberCode(calendar.getMemberCode(), Sort.by("indexNo").descending());
        log.info("[CalendarService](saveByCalendar) lastIndexNo : {}", lastIndexNo);

        calendar.setIndexNo(lastIndexNo.isEmpty() ? 1 : lastIndexNo.get().getIndexNo() + 1);
        Calendar entityCalendar = modelMapper.map(calendar, Calendar.class);

        log.info("[CalendarService](saveByCalendar) entityCalendar : {}", entityCalendar);

        calendarRepository.save(entityCalendar);
    }

    @Transactional
    public void updateById(CalendarDTO calendar) {
        log.info("[CalendarService](updateById) calendar : {}", calendar);

        Calendar entityCalendar = calendarRepository
                .findById(calendar.getId())
                .orElseThrow(() -> new NotFindDataException("캘린더를 찾을 수 없습니다"));

        log.info("[CalendarService](updateById) entityCalendar : {}",entityCalendar);

        entityCalendar.update(calendar);

    }

    @Transactional
    public void updateDefaultCalender(CalendarDTO calendarDTO){

        log.info("[CalendarService](updateDefaultCalender) calendarDTO : {}", calendarDTO);

        Optional<Calendar> prevDefaultCalendar = calendarRepository.findByMemberCodeAndDefaultCalendar(calendarDTO.getMemberCode(), true);

        if(prevDefaultCalendar.isPresent()) {
            log.info("[CalendarService](updateDefaultCalender) prevDefaultCalendar : {}", prevDefaultCalendar.get());
            prevDefaultCalendar.get().setDefaultCalendar(false);
        }

        Calendar afterDefaultCalendar = calendarRepository
                .findById(calendarDTO.getId())
                .orElseThrow(() -> new NotFindDataException("캘린더를 찾을 수 없습니다"));

        log.info("[CalendarService](updateDefaultCalender) afterDefaultCalendar : {}", afterDefaultCalendar);

        afterDefaultCalendar.setDefaultCalendar(true);
    }

    @Transactional
    public void updateCalendarIndexNo(Map<String, String> info){

        log.info("[CalendarService](updateCalendarIndexNo) info : {}", info);

        Optional<Calendar> cur = calendarRepository.findById(Integer.valueOf(info.get("id")));

        int tempSeq = cur.orElseThrow(() -> new NotFindDataException("캘린더를 찾을 수 없습니다.")).getIndexNo();

        if(info.get("direction").equals("prev")){
            Optional<Calendar> prev = calendarRepository.findFirstByMemberCodeAndIndexNoBefore(Integer.valueOf(info.get("memberCode")),tempSeq);
            int prevIndexNo = prev.orElseThrow(() ->
                    new NotFindDataException("캘린더가 존재 하지 않습니다.")
            ).getIndexNo();
            cur.get().setIndexNo(prevIndexNo);
            prev.get().setIndexNo(tempSeq);

            log.info("[CalendarService](updateCalendarIndexNo) prev : {}", prev);
            log.info("[CalendarService](updateCalendarIndexNo) cur : {}", cur);
            log.info("[CalendarService](updateCalendarIndexNo) tempSeq : {}", tempSeq);


        }else if(info.get("direction").equals("next")){
            Optional<Calendar> next = calendarRepository.findFirstByMemberCodeAndIndexNoAfter(Integer.valueOf(info.get("memberCode")),tempSeq, Sort.by("indexNo").ascending());
            int nextIndexNo = next.orElseThrow(() ->
                    new NotFindDataException("캘린더가 존재하지 않습니다.")).getIndexNo();
            cur.get().setIndexNo(nextIndexNo);
            next.get().setIndexNo(tempSeq);

            log.info("[CalendarService](updateCalendarIndexNo) next : {}", next);
            log.info("[CalendarService](updateCalendarIndexNo) cur : {}", cur);
            log.info("[CalendarService](updateCalendarIndexNo) tempSeq : {}", tempSeq);


        }else {
            throw new InvalidRequestException("잘못된 요청입니다.");
        }


    }

    @Transactional
    public void deleteById(List<Integer> calendarList) {

        int memberCode = 2; // 삭제 예정

        log.info("[CalendarService](deleteById) calendarList : {}", calendarList);

        calendarList.forEach(item ->{
            log.info("[CalendarService](deleteById) item : {}", item);
            scheduleRepository.deleteAllByRefCalendar(item);
            favotriteCalendarRepository.deleteByRefCalendar(item);
            calendarRepository.deleteById(item);
        });

        // indexNo 재정렬 로직 추가
        // 삭제할 indexNo 기준 이후의 값들만 조회해서 수정

        Optional<Calendar> defaultCalendar = calendarRepository.findByMemberCodeAndDefaultCalendar(memberCode, true);
        defaultCalendar.orElseThrow(()->
                new NotEnoughDateException("기본 캘린더가 존재 하지 않습니다."));

        Optional<Calendar> findFirstCalendar = calendarRepository.findFirstByMemberCode(memberCode, Sort.by("indexNo").ascending());
        findFirstCalendar.orElseThrow(()->
                        new NotFindDataException("캘린더가 존재 하지 않습니다."))
                .setDefaultCalendar(true);



    }


    public Page<CalendarDTO> openCalendarList(Integer memberCode, Pageable pageable) {

        Page<Calendar> calendarList = calendarRepository.findByDepartmentCodeAndOpenStatusAndMemberCodeNot(null ,true, memberCode, pageable);
        log.info("[CalendarService](openCalendarList) calendarList : {}",calendarList);

        if(calendarList.getContent().size() == 0 ) throw new NotFindDataException("조회할 데이터가 없습니다.");

        return calendarList
                .map(calendar -> {
                   CalendarDTO calendarDTO = modelMapper.map(calendar, CalendarDTO.class);

                   calendarDTO.setScheduleList(null);

                   calendarDTO.setFavoriteCalendar(
                           calendarDTO.getFavoriteCalendar().stream()
                                   .map(favoriteCalendarDTO -> {
                                       favoriteCalendarDTO.setCalendar(null);
                                       return favoriteCalendarDTO;

                                   }).collect(Collectors.toList()));

                   MemberDTO memberDTO = new MemberDTO();
                   memberDTO.setMemberName(calendarDTO.getMember().getMemberName());
                   memberDTO.setMemberNo(calendarDTO.getMember().getMemberNo());
                   calendarDTO.setMember(memberDTO);

                   log.info("[CalendarService](openCalendarList) calendarDTO : {}",calendarDTO);

                   return calendarDTO;
                });

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

    public List<CalendarDTO> myCalendarList(int memberCode,int departmentCode) {

        List<Calendar> calendarList =
//                calendarRepository.findByMemberCode(memberCode, null, Sort.by(Sort.Direction.ASC, "indexNo"));
                calendarRepository.findByMemberCode(memberCode, departmentCode);

        return calendarList.stream()
                .map(calendar -> modelMapper.map(calendar, CalendarDTO.class))
                .map(calendarDTO -> {
                    calendarDTO.setFavoriteCalendar(null);
                    calendarDTO.setScheduleList(null);
                    calendarDTO.setMember(null);
                    return calendarDTO;
                }).collect(Collectors.toList());

    }
}
