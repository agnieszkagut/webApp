package com.ag.studies.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "calendar_entries_table", schema = "projekty")
public class CalendarEntriesTableEntity {
    private Integer calendarEntryId;
    private Integer projectId;
    private Timestamp date;
    private String description;
    private ProjectTableEntity projectTableByProjectId;

    @Id
    @GeneratedValue
    @Column(name = "calendar_entry_id", nullable = false)
    public Integer getCalendarEntryId() {
        return calendarEntryId;
    }

    public void setCalendarEntryId(Integer calendarEntryId) {
        this.calendarEntryId = calendarEntryId;
    }

    @Basic
    @Column(name = "project_id", nullable = false)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 250)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEntriesTableEntity that = (CalendarEntriesTableEntity) o;
        return Objects.equals(calendarEntryId, that.calendarEntryId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendarEntryId, projectId, date, description);
    }

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false, insertable=false, updatable=false)
    public ProjectTableEntity getProjectTableByProjectId() {
        return projectTableByProjectId;
    }

    public void setProjectTableByProjectId(ProjectTableEntity projectTableByUserId) {
        this.projectTableByProjectId = projectTableByProjectId;
    }

}
