package com.pro.infomate.calendar.repository;

import com.pro.infomate.calendar.entity.Participant;
import com.pro.infomate.calendar.entity.ParticipantPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, ParticipantPK> {
    void deleteByScheduleCode(int scheduleCode);
}
