package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.DayPerCountDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.Schedule;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.ScheduleRepository;
import com.pro.infomate.exception.NotAuthenticationMember;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final ModelMapper modelMapper;

//    안씀
//    public List<ScheduleDTO> findAllScheduleByCalendarByMemberCode(Integer memberCode) {
//
//        List<Schedule> scheduleList = scheduleRepository.findAllScheduleByCalendarByMemberCode(memberCode);
//
//        log.info("[ScheduleService](updateById) scheduleList : {}", scheduleList);
//
//        if(scheduleList.isEmpty() || scheduleList.size() == 0) throw new NotFindDataException("데이터를 찾을 수 없습니다.");
//
//        return scheduleList.stream()
//                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
//                .map(scheduleDTO -> {
//                    scheduleDTO.setCalendar(null);
//                    return scheduleDTO;
//                })
//                .collect(Collectors.toList());
//    }

    public ScheduleDTO findById(int scheduleId, int memberCode){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFindDataException("일정을 찾을 수 없습니다"));

        if (!schedule.getCalendar().getMemberCode().equals(memberCode)
                && !schedule.getCalendar().getOpenStatus()){
            throw new NotFindDataException("데이터를 찾을 수 없습니다.");
        }

        return Stream.of(schedule)
                .map(value -> modelMapper.map(value, ScheduleDTO.class))
                .map(scheduleDTO -> {
                    CalendarDTO calendarDTO = scheduleDTO.getCalendar();
                    calendarDTO.setMember(null);
                    calendarDTO.setFavoriteCalendar(null);
                    calendarDTO.setScheduleList(null);

                    scheduleDTO.setCalendar(calendarDTO);

                    scheduleDTO.setParticipantList(
                            scheduleDTO.getParticipantList().stream()
                                    .map(participantDTO -> {
                                        participantDTO.setSchedule(null);
                                        return participantDTO;
                                    }).collect(Collectors.toList()));

                    return scheduleDTO;
                }).findFirst().get();
    }

    @Transactional
    public void updateById(ScheduleDTO schedule, int memberCode) {
        log.info("[ScheduleService](updateById) schedule : {}",schedule);

        Schedule entitySchedule = scheduleRepository
                .findById(schedule.getId())
                .orElseThrow(() -> new NotFindDataException("일정을 찾을 수 없습니다"));

        Member member = memberRepository.findById(memberCode)
                .orElseThrow(()-> new NotFindDataException("유저가 존재 하지 않습니다"));

        if(entitySchedule.getCalendar().getMember().getMemberCode() != memberCode
            && entitySchedule.getCalendar().getDepartmentCode() != null
            && !entitySchedule.getCalendar().getDepartmentCode().equals(member.getDepartment().getDeptCode())
        ){
            throw new NotAuthenticationMember("수정 권한이 존재 하지 않습니다.");
        }

        log.info("[ScheduleService](updateById) entitySchedule : {}",entitySchedule);

        entitySchedule.update(schedule);

    }

    @Transactional
    public void deleteById(int scheduleId, int memberCode) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new NotFindDataException("삭제할 일정을 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberCode)
                .orElseThrow(()-> new NotFindDataException("계정을 찾을 수 없습니다."));

        if (!schedule.getCalendar().getMemberCode().equals(memberCode)
            && schedule.getCalendar().getDepartmentCode() != null
            && !schedule.getCalendar().getDepartmentCode().equals(
                    member.getDepartment().getDeptCode())){
            throw new NotAuthenticationMember("삭제할 권한이 존재하지 않습니다.");
        }

        scheduleRepository.delete(schedule);

    }

    public List<ScheduleDTO> findScheduleSearch(String keyword, Integer userId) {
//        List<Schedule> scheduleList = scheduleRepository.findScheduleSearch(keyword, userId);
        List<Schedule> scheduleList = new ArrayList<>();
        return scheduleList.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Object insertSchedule(ScheduleDTO scheduleDTO, int memberCode) {
        log.info("[ScheduleService](insertSchedule) scheduleDTO : {}", scheduleDTO);
        log.info("[ScheduleService](insertSchedule) memberCode : {}", memberCode);

        Calendar calendar = calendarRepository.findById(scheduleDTO.getRefCalendar())
                .orElseThrow(()-> new NotFindDataException("캘린더를 찾을 수 없습니다."));
        Member member = memberRepository.findById(memberCode)
                .orElseThrow(()-> new NotFindDataException("계정을 찾을 수 없습니다."));

        log.info("[ScheduleService](insertSchedule) member.getDepartment().getDeptCode() : {}", member.getDepartment().getDeptCode());
        log.info("[ScheduleService](insertSchedule) calendar.getDepartmentCode() : {}", calendar.getDepartmentCode());
        log.info("[ScheduleService](insertSchedule) calendar.getMemberCode() : {}", calendar.getMemberCode());

        if (calendar.getDepartmentCode() != null
            && calendar.getDepartmentCode() != member.getDepartment().getDeptCode()
            && calendar.getMemberCode() != memberCode){
            throw new NotAuthenticationMember("수정 권한 없는 계정입니다.");
        }

        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        log.info("[ScheduleService](insertSchedule) schedule : {}",schedule);
        return scheduleRepository.save(schedule);
    }

    public List<ScheduleDTO> reminder(int memberCode) {

        LocalDate today = LocalDate.now();

        log.info("[ScheduleService](reminder) today : {}",today);

        List<Schedule> scheduleList =
                scheduleRepository.findThreeDays(memberCode, today.atStartOfDay(), today.plusDays(2).atTime(LocalTime.MAX));

        log.info("[ScheduleService](reminder) scheduleList : {}",scheduleList);

        return scheduleList.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .map(scheduleDTO -> {
                    scheduleDTO.setCalendar(null);
                    return scheduleDTO;
                })
                .collect(Collectors.toList());
    }

    public List<DayPerCountDTO> dayPerCount(LocalDate startDay, LocalDate endDay, int memberCode) {
//        List<Object[]> daysCount = scheduleRepository.dayPerCount(startDay, endDay, memberCode);
        List<DayPerCountDTO> daysCount  = scheduleRepository.dayPerCount(startDay.atStartOfDay(), endDay.atStartOfDay(), memberCode);
        log.info("[ScheduleService](dayPerCount) daysCount : {}", daysCount);

//        List<DayPerCountDTO> dayPerCountDTOS = new ArrayList<>();
//        for (Object[] item : daysCount){
//            dayPerCountDTOS.add(new DayPerCountDTO(LocalDateTime.parse((String) item[1]), (Long) item[0]));
//        }


//        log.info("[ScheduleService](dayPerCount) dayPerCountDTOS : {}", dayPerCountDTOS);
        return daysCount;
    }
// member 엔티티 완료 된 후 수정
//    public List<ScheduleDTO> findAllScheduleSearch(int memberCode, String keyword){
//        List<Schedule> scheduleList = scheduleRepository.findAllBySubjectAndContentSearch(memberCode, keyword);
//
//        log.info("[ScheduleService](reminder) scheduleList : {}", scheduleList);
//
//        return scheduleList.stream()
//                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
//                .map(scheduleDTO -> {
//                    scheduleDTO.setCalendar(null);
//                    return scheduleDTO;
//                })
//                .collect(Collectors.toList());
//
//        return null;
//
//    }
}
