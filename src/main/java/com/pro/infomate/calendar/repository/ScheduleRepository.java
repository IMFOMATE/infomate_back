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
