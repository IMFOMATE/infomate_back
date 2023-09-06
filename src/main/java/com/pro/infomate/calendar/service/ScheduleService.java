package com.pro.infomate.calendar.service;

import com.pro.infomate.calendar.dto.CalendarDTO;
import com.pro.infomate.calendar.dto.DayPerCountDTO;
import com.pro.infomate.calendar.dto.ScheduleDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.Participant;
import com.pro.infomate.calendar.entity.ParticipantPK;
import com.pro.infomate.calendar.entity.Schedule;
import com.pro.infomate.calendar.repository.CalendarRepository;
import com.pro.infomate.calendar.repository.ParticipantRepository;
import com.pro.infomate.calendar.repository.ScheduleRepository;
import com.pro.infomate.exception.NotAuthenticationMember;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.member.dto.MemberDTO;
import com.pro.infomate.member.dto.MemberSumamryDTO;
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
import java.util.Arrays;
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
    private final ParticipantRepository participantRepository;
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
//                    scheduleDTO.setParticipantList(null);
                    scheduleDTO.setParticipantList(
                            scheduleDTO.getParticipantList().stream()
                                    .map(participantDTO -> {
                                        participantDTO.setSchedule(null);
//                                        participantDTO.setMember(null);
                                        MemberDTO memberDTO = new MemberDTO();
                                        memberDTO.setMemberCode(participantDTO.getMember().getMemberCode());
                                        memberDTO.setMemberName(participantDTO.getMember().getMemberName());

                                        participantDTO.setMember(memberDTO);
                                        log.info("[ScheduleService](findById) participantDTO : {}",participantDTO);
                                        return participantDTO;
                                    }).collect(Collectors.toList())
                    );

                    return scheduleDTO;

                }).findFirst().get();
    }

    @Transactional
    public void updateById(ScheduleDTO scheduleDTO, int memberCode) {
        log.info("[ScheduleService](updateById) schedule : {}",scheduleDTO);

        Schedule entitySchedule = scheduleRepository
                .findById(scheduleDTO.getId())
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

        List<Participant> participant = scheduleDTO.getParticipantList().stream().map(item -> modelMapper.map(item, Participant.class)).collect(Collectors.toList());
        participant = participant.stream().map(participant1 -> {
            participant1.setScheduleCode(scheduleDTO.getId());
            return participant1;
        }).collect(Collectors.toList());

        participantRepository.deleteByScheduleCode(scheduleDTO.getId());
        participantRepository.saveAll(participant);
        entitySchedule.update(modelMapper.map(scheduleDTO, Schedule.class));

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

//        participantRepository.deleteByScheduleCode(schedule.getId());
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
    public void insertSchedule(ScheduleDTO scheduleDTO, int memberCode) {
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

        log.info("[ScheduleService](insertSchedule) schedule : {}", schedule.getParticipantList());
        schedule.setParticipantList(null);


        scheduleRepository.save(schedule);


        List<Participant> participant = scheduleDTO.getParticipantList().stream().map(item -> modelMapper.map(item, Participant.class)).collect(Collectors.toList());
        participant = participant.stream().map(participant1 -> {
            participant1.setScheduleCode(schedule.getId());
            return participant1;
        }).collect(Collectors.toList());


        log.info("[ScheduleService](insertSchedule) participant : {}",participant);
        participantRepository.saveAll(participant);
//
        log.info("[ScheduleService](insertSchedule) schedule : {}",schedule);
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
