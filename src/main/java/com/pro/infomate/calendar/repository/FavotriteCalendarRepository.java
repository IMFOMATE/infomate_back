package com.pro.infomate.calendar.repository;

import com.pro.infomate.calendar.entity.FavoriteCalendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FavotriteCalendarRepository extends JpaRepository<FavoriteCalendar, Integer> {


    @Query(value = "SELECT f " +
            "FROM FavoriteCalendar f " +
            "WHERE f.refCalendar IN (SELECT c.id " +
                                      "FROM Calendar c " +
                                     "WHERE c.openStatus IS TRUE " +
                                       "AND NOT c.memberCode = :memberCode)")
    Page<FavoriteCalendar> findAllByMemberCode(Integer memberCode, Pageable pageable);

    void deleteByRefCalendar(Integer calendarNo);

    Optional<FavoriteCalendar> findByMemberCodeAndRefCalendar(Integer memberCode, Integer refCalendar);
    @Query( value = "SELECT fc " +
                      "FROM FavoriteCalendar fc " +
                     "WHERE fc.refCalendar IN (SELECT c.id " +
                                                "FROM Calendar c " +
                                               "WHERE c.memberCode = :memberCode)")
    Page<FavoriteCalendar> findByCalendarByMemberCode(Integer memberCode, Pageable pageable);
}
