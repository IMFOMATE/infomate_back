package com.pro.infomate.work.service;

import com.pro.infomate.exception.CannotFinishWorkException;
import com.pro.infomate.exception.NotFindDataException;
import com.pro.infomate.exception.WorkAlreadyExistsException;
import com.pro.infomate.member.entity.Member;
import com.pro.infomate.member.repository.MemberRepository;
import com.pro.infomate.work.dto.response.WorkResponse;
import com.pro.infomate.work.entity.Off;
import com.pro.infomate.work.entity.Work;
import com.pro.infomate.work.entity.WorkStatus;
import com.pro.infomate.work.repository.OffRepository;
import com.pro.infomate.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkService {

  private final MemberRepository memberRepository;
  private final WorkRepository workRepository;
  private final OffRepository offRepository;

  //1. 출근
  // 출근 버튼누르면 멤버코드로 work에 insert
  // 만약 오늘 날짜의 출근이 이미 됐으면 이미출근했다는 오류발생
  @Transactional
  public void attend(Integer memberCode){
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Optional<Work> existing = workRepository.findByYearMonthAndMember(LocalDate.now(), member);

    if (existing.isPresent())
      throw new WorkAlreadyExistsException("오늘 이미 출근하셨습니다.");

    Work work = Work.builder()
            .timeStart(LocalDateTime.now())
            .yearMonth(LocalDate.now())
            .workStatus(WorkStatus.PROGRESS)
            .member(member)
            .build();

    workRepository.save(work);
  }

  //2. 퇴근
  //퇴근버튼 누르면 9시간 이하일 경우 퇴근할 수 없음
  // 9시간 이상이면 퇴근 컬럼 update하고, 근무상태를 done
  //휴가 신청이 완료되면 해당날짜에 approval에서 insert해주자

  @Transactional
  public void finish(Integer memberCode) {
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));

    Work work = workRepository.findByYearMonthAndMember(LocalDate.now(), member).orElseThrow(() -> new NotFindDataException("출근정보가 없습니다"));

    LocalDateTime nowTime = LocalDateTime.now();
    LocalDateTime attendTime = work.getTimeStart();

    // 근무 시간 계산
    Duration workDuration = Duration.between(attendTime, nowTime);

    // 9시간 미만 근무일 경우 퇴근할 수 없음
//    if (workDuration.toHours() < 1) {
//      throw new CannotFinishWorkException("근무 시간이 9시간 미만입니다. 퇴근할 수 없습니다.");
//    }

    // 근무 상태를 'done'으로 변경하고 퇴근 시간 설정
    work.setWorkStatus(WorkStatus.DONE);
    work.setTimeEnd(nowTime);

  }

  public WorkResponse findByDate(LocalDate date, Integer memberCode){
    Member member = memberRepository.findById(memberCode).orElseThrow(() -> new NotFindDataException("회원정보가 없습니다"));
    Work work = workRepository.findByYearMonthAndMember(date, member).orElseThrow(() -> new NotFindDataException("출근정보가 없습니다"));

    return WorkResponse.builder()
            .startTime(work.getTimeStart())
            .endTime(work.getTimeEnd())
            .workStatus(work.getWorkStatus())
            .build();

  }

  //만약 반차가 있을 경우 해당 날짜의 반차 니까 근무시간을 -4
  //상태를 반차
}
