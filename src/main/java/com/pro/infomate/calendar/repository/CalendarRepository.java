package com.pro.infomate.calendar.repository;


import com.pro.infomate.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    Optional<Calendar> findByMemberCodeAndDefaultCalendar(Integer userId, boolean isTrue);

    List<Calendar> findByDepartmentCodeAndOpenStatus(Integer departmentCode, boolean openStatus);

    List<Calendar> findByMemberCode(int memberCode);
}
