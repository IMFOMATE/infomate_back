package com.pro.infomate.calendar;

import com.pro.infomate.calendar.service.CalendarService;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class Test {

    @Autowired
    CalendarService calendarService;
    @org.junit.jupiter.api.Test
    @DisplayName("savecalendar")
    void 부서생성캘린더test() {
        calendarService.saveDepartmentCalendarRegist(27);
    }

}



