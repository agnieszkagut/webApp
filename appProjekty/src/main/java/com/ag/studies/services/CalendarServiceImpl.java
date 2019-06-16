package com.ag.studies.services;

import com.ag.studies.models.CalendarEntriesTableEntity;
import com.ag.studies.repositories.CalendarEntriesTableEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CalendarServiceImpl implements CalendarService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CalendarEntriesTableEntityRepository calendarRepository;
    public List<CalendarEntriesTableEntity> findAll() {
        return calendarRepository.findAll();
    }

    @Override
    public void delete(Long calendarEntryId) {
        calendarRepository.deleteById(calendarEntryId);
    }

    @Override
    public List<CalendarEntriesTableEntity> findByProjectId(Long projectId) {
        return calendarRepository.findByProjectId(projectId);
    }

    @Override
    public void addNewEntry(Long projectId, CalendarEntriesTableEntity newEntry) {
        calendarRepository.save(newEntry);
    }

    @Override
    public List<CalendarEntriesTableEntity> findByDate(String date) {
        String strDateFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat, Locale.UK);
        List<CalendarEntriesTableEntity> listOfEntries = new ArrayList<CalendarEntriesTableEntity>();
        for(CalendarEntriesTableEntity c : calendarRepository.findAll()){
            Date noteDate = new Date(c.getDate().getTime());
            String sqlDate = sdf.format(noteDate);
            logger.info("Sprawdzana data: "+ sqlDate);
            if(sqlDate.equals(date)) {
                listOfEntries.add(c);
                logger.info("Dopisywana data: "+ sqlDate);
            }
        }
        return listOfEntries;
    }
}
