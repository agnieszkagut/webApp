package com.ag.studies.repositories;

import com.ag.studies.models.CalendarEntriesTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface CalendarEntriesTableEntityRepository extends JpaRepository<CalendarEntriesTableEntity, Long> {
    List<CalendarEntriesTableEntity> findByProjectId(Long projectId);

    List<CalendarEntriesTableEntity> findByDate(Timestamp selectedDate);
}