package com.ag.studies.controllers;

import com.ag.studies.EntityNotFoundException;
import com.ag.studies.models.CalendarEntriesTableEntity;
import com.ag.studies.services.CalendarServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
@CrossOrigin(allowCredentials="true")
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CalendarServiceImpl calendarService;

    @GetMapping
    public List<CalendarEntriesTableEntity> getAllEntries(){
        return calendarService.findAll();
    }

    @GetMapping("/date/{date}")
    public List<CalendarEntriesTableEntity> getDateEntries(@PathVariable(value = "date") String date) throws EntityNotFoundException{
        logger.info("Odebrano datę: "+ date);
        List<CalendarEntriesTableEntity> listOfEntries = calendarService.findByDate(date);
        logger.info("Wysłano listę: "+ listOfEntries);
        return listOfEntries;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addEntry(@RequestBody IncomingEntry newEntry){
        calendarService.addNewEntry(newEntry.getSelectedDate(), newEntry.getProjectId(), newEntry.getInscription());
    }

    @DeleteMapping("/{entryId}")
    public void deleteById(@PathVariable(value = "entryId") Long entryId){
        calendarService.delete(entryId);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class IncomingEntry{
        Date selectedDate;
        private Long projectId;
        String inscription;
    }

}
