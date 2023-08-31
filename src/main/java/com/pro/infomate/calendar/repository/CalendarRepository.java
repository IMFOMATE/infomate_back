package com.pro.infomate.calendar.repository;


import com.pro.infomate.calendar.entity.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    Optional<Calendar> findByMemberCodeAndDefaultCalendar(Integer memberCoode, Boolean defaultCalendar);

    Optional<Calendar> findFirstByMemberCode(int memberCode, Sort sort);

    Optional<Calendar> findByDepartmentCode(int deptCode);

    Page<Calendar> findByDepartmentCodeAndOpenStatusAndMemberCodeNot(Integer departmentCode, boolean openStatus, Integer memberCode, Pageable pageable);

    Optional<Calendar> findFirstByMemberCodeAndIndexNoBefore(Integer id, Integer indexNo);
    Optional<Calendar> findFirstByMemberCodeAndIndexNoAfter(Integer id, Integer indexNo, Sort sort);
//    @Query(value = "SELECT s FROM Calendar s " +
//                     "JOIN Member m on m.memberCode = s.memberCode " +
//                    "LEFT JOIN FavoriteCalendar f on f.memberCode = :memberCode " +
//                    "WHERE NOT s.memberCode = :memberCode " +
//                    "AND s.openStatus = true " +
//                    "AND s.departmentCode IS NULL"
//    )
//    List<Calendar> findByPublicCalendarList(Integer memberCode);


    @Query(value = "SELECT c FROM Calendar c " +
                    "WHERE c.memberCode = :memberCode " +
                       "OR c.id IN (SELECT f.refCalendar FROM FavoriteCalendar f " +
                                    "WHERE f.memberCode = :memberCode " +
                                      "AND f.approvalStatus = 'APPROVAL') " +
                       "OR c.departmentCode = 1 "  +
                       "OR c.departmentCode = :departmentCode " +
                      "AND c.openStatus = true " +
                    "ORDER BY c.indexNo ASC ")
    List<Calendar> findByMemberCode(int memberCode, int departmentCode);

//    List<Calendar> findAllByMemberCodeAndDepartmentCode(int memberCode, Integer departmentCode, Sort indexNo);

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

    List<Calendar> findAllByMemberCodeAndDefaultCalendar(int memberCode, boolean b);

    List<Calendar> findAllByMemberCode(int memberCode, Sort sort);
}
