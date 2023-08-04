package com.pro.infomate.calendar.repository;


import com.pro.infomate.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    Calendar findByMemberCodeAndDefaultCalendar(Integer userId, boolean isTrue);

//    @Query(value = "SELECT c FROM Calendar c WHERE NOT c.userId = :userId AND c.openStatus = true")
//    List<Calendar> findByOpenCalendar(Integer userId);


    List<Calendar> findByMemberCodeNotAndOpenStatus(Integer userId, boolean openStatus);

    List<Calendar> findByMemberCode(int memberId);
}
