package com.pro.infomate.calendar.repository;


import com.pro.infomate.calendar.dto.CalendarSummaryDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.CalendarSummary;
import com.pro.infomate.calendar.entity.Schedule;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    Optional<Calendar> findByMemberCodeAndDefaultCalendar(Integer userId, boolean isTrue);

    List<Calendar> findByDepartmentCodeAndOpenStatus(Integer departmentCode, boolean openStatus);

    List<Calendar> findByMemberCode(int memberCode);

    List<Calendar> findAllByMemberCodeAndDepartmentCode(int memberCode, Integer departmentCode, Sort indexNo);

    @Query(value = "SELECT COUNT(s.id) AS amount, " +
                          "TRUNC(s.endDate) AS day " +
                     "FROM Schedule s " +
                     "JOIN Calendar c on c.id = s.refCalendar " +
                    "WHERE c.departmentCode IS NULL " +
                      "AND SUBSTR(s.endDate, 1, 7)  = SUBSTR(:localDate, 1, 7)  " +
                      "AND c.memberCode = :memberCode " +
                       "OR c.departmentCode = 0 " +
                       "OR c.departmentCode IN (SELECT m.memberCode " +  // membercode 수정예정 departmentCode
                                                 "FROM Member m " +
                                                "WHERE m.memberCode = :memberCode) " +
                                                "GROUP BY TRUNC(s.endDate)")
    List<Object[]> findAllByDaysCount(Integer memberCode, LocalDate localDate);
}
