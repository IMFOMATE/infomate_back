package com.pro.infomate.calendar.repository;

import com.pro.infomate.calendar.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query(value = "SELECT s " +
                     "FROM Schedule s " +
                    "WHERE s.refCalendar IN (SELECT c.id " +
                                              "FROM Calendar c " +
                                             "WHERE c.memberCode = :memberCode)")
    List<Schedule> findAllScheduleByCalendarByMemberCode(int memberCode);

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
