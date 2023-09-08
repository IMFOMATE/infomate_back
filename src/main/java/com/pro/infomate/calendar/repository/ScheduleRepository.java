package com.pro.infomate.calendar.repository;

import com.pro.infomate.calendar.dto.DayPerCountDTO;
import com.pro.infomate.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query(value = "SELECT s " +
                     "FROM Schedule s " +
                    "WHERE s.refCalendar IN (SELECT c.id " +
                                              "FROM Calendar c " +
                                             "WHERE c.memberCode = :memberCode)")
    List<Schedule> findAllScheduleByCalendarByMemberCode(int memberCode);

    @Query(value = "SELECT s " +
                     "FROM Schedule s " +
                     "JOIN Calendar c ON c.id = s.refCalendar " +
                    "WHERE c.departmentCode = (SELECT d.deptCode " +
                                                "FROM Member m " +
                                                "JOIN m.department d " +
                                               "WHERE m.memberCode = :memberCode) " +
                      "AND s.startDate BETWEEN :startDate AND :endDate " )
    List<Schedule> findThreeDays(int memberCode, LocalDateTime startDate, LocalDateTime endDate);


    void deleteAllByRefCalendar(int calendarId);


    @Query(value = "SELECT COUNT(s.id) as count, TRUNC(s.startDate) as date " +
                     "FROM Schedule s " +
                     "JOIN s.calendar c "+
                    "WHERE c.memberCode = :memberCode " +
                      "AND s.startDate BETWEEN :startDay AND :endDay " +
                    "GROUP BY trunc(s.startDate) ")
    List<DayPerCountDTO> dayPerCount(LocalDateTime startDay, LocalDateTime endDay, int memberCode);

}
