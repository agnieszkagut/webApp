package com.ag.studies.services;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.CalendarEntriesTableEntity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface CalendarService {
    List<CalendarEntriesTableEntity> findAll();

    void delete(Long calendarEntryId);

    List<CalendarEntriesTableEntity> findByProjectId(Long projectId);

    void addNewEntry(Date selectedDate, Long projectId, String newInscription);

    List<CalendarEntriesTableEntity> findByDate(String date) throws EntityNotFoundException;
}
