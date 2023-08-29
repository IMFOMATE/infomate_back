package com.pro.infomate.calendar.repository;


import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.entity.Calendar;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavotriteCalendarRepository extends JpaRepository<FavoriteCalendar, Integer> {
    List<FavoriteCalendar> findAllByRefCalendar(Integer favoriteId);

    List<FavoriteCalendar> findAllByMemberCode(Integer userId);

    Optional<FavoriteCalendar> findByMemberCodeAndRefCalendar(Integer memberCode, Integer refCalendar);
    @Query( value = "SELECT fc " +
                      "FROM FavoriteCalendar fc " +
                     "WHERE fc.refCalendar IN (SELECT c.id " +
                                                "FROM Calendar c " +
                                               "WHERE c.memberCode = :memberCode)")
    List<FavoriteCalendar> findByCalendarByMemberCode(Integer memberCode);
}
