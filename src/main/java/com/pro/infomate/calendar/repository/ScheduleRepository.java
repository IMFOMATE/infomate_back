package com.pro.infomate.calendar.repository;

import com.pro.infomate.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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
                    "WHERE s.endDate between :startDate AND :endDate " +
                      "AND c.memberCode = :memberCode")
    List<Schedule> findAllByEndDateBetweenThree(int memberCode, LocalDateTime startDate, LocalDateTime endDate);


    void deleteAllByRefCalendar(int calendarId);

// 부서 코드 조인으로 인한 member 엔티티 후 수정 예정
//    @Query(value = "SELECT s "+
//                     "FROM Schedule s " +
//                     "JOIN Calendar c " +
//                    "WHERE c.memberCode = :memberCode " +
//                      "AND c.departmentCode IS NULL " +
//                       "OR c.departmentCode = 0 " +
//                       "OR c.departmentCode IN (SELECT m.memberCode " +
//                                                 "FROM Member m " +
//                                                "WHERE m.memberCode = :memberCode) " +
//                      "AND Schedule.title LIKE '%' || :keyword || '%' " +
//                       "OR Schedule.content LIKE '%' || :keyword || '%' " +
//                    "ORDER BY s.endDate DESC")
//    List<Schedule> findAllBySubjectAndContentSearch(int memberCode, String Keyword);

//    List<Schedule> findAllByRefCalendarId(Integer calendarId);

//    @Query(value = "SELECT sc2 " +
//                    "FROM Schedule sc2 " +
//                    "WHERE sc2.id IN "+
//                    "(SELECT MAX(sc.id) as id " +
//                            "FROM Schedule sc " +
//                            "JOIN Calendar c " +
//                            // join 사용자 entitry 예정
//                           "WHERE c.userID = :userId " +
//                           "GROUP BY sc.endDate)"
//    )
//    List<Schedule> findRecentScheduleByUserId(Integer userId);

//    @Query(value = "SELECT sc " +
//            "FROM Schedule sc " +
//            "JOIN Calendar c " +
//            "JOIN FavoriteCalendar fc " +
//            "WHERE " + // 유저 pk 같은 것
//            "OR sc.address Like '%' || :keyword || '%' " +
//            "OR sc.subject Like '%' || :keyword || '%' " +
//            "OR sc.content Like '%' || :keyword || '%' ")
//    List<Schedule> findScheduleSearch(String keyword, Integer userId);
}
