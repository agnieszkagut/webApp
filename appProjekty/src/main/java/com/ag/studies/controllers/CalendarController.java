package com.ag.studies.controllers;

import com.ag.studies.models.CalendarEntriesTableEntity;
import com.ag.studies.services.CalendarServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(allowCredentials="true")
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CalendarServiceImpl calendarService;

    @GetMapping("/all")
    public List<CalendarEntriesTableEntity> getAllEntries(){
        return calendarService.findAll();
    }

    @GetMapping("/project/{projectId}")
    public List<CalendarEntriesTableEntity> getprojectEntries(@PathVariable(value = "projectId") Long projectId){
        return calendarService.findByProjectId(projectId);
    }

    @GetMapping("/events/entries/{date}")
    public List<CalendarEntriesTableEntity> getDateEntries(@PathVariable(value = "date") String date){
        logger.info("Odebrano datę: "+ date);
        List<CalendarEntriesTableEntity> listOfEntries = calendarService.findByDate(date);
        logger.info("Wysłano listę: "+ listOfEntries);
        return listOfEntries;
    }

    @PostMapping(path = "/entry/{projectId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void addEntry(@PathVariable(value = "projectId") Long projectId, @RequestBody CalendarEntriesTableEntity newEntry){
        calendarService.addNewEntry(projectId, newEntry);
    }

    @DeleteMapping("/entry/{calendarEntryId}")
    public void deletebyId(@PathVariable(value = "calendarEntryId") Long calendarEntryId){
        calendarService.delete(calendarEntryId);
    }

}
