package com.pro.infomate.calendar.repository;


import com.pro.infomate.calendar.dto.FavoriteCalendarDTO;
import com.pro.infomate.calendar.entity.FavoriteCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavotriteCalendarRepository extends JpaRepository<FavoriteCalendar, Integer> {
    List<FavoriteCalendar> findAllByRefCalendar(Integer favoriteId);

    List<FavoriteCalendarDTO> findAllByMemberCode(Integer userId);
}
