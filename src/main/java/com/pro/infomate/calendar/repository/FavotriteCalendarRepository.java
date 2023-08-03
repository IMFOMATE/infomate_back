package com.pro.infomate.calendar.repository;

import com.prev.clndr.dto.FavoriteCalendarDTO;
import com.prev.clndr.entity.FavoriteCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavotriteCalendarRepository extends JpaRepository<FavoriteCalendar, Integer> {
    List<FavoriteCalendar> findAllByRefCalendarId(Integer favoriteId);

    List<FavoriteCalendarDTO> findAllByUserId(Integer userId);
}
