package com.ag.studies.models;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "sponsorship_table", schema = "projekty")
public class SponsorshipTableEntity {
    private Long sponsorshipId;
    private Long projectId;
    private String name;
    private BigDecimal value;
    private ProjectTableEntity projectTableByProjectId;

    @Id
    @Column(name = "sponsorship_id", nullable = false)
    public Long getSponsorshipId() {
        return sponsorshipId;
    }

    public void setSponsorshipId(Long sponsorshipId) {
        this.sponsorshipId = sponsorshipId;
    }

    @Basic
    @Column(name = "project_id", nullable = false)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "value", nullable = false, precision = 2)
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SponsorshipTableEntity that = (SponsorshipTableEntity) o;
        return Objects.equals(sponsorshipId, that.sponsorshipId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sponsorshipId, projectId, name, value);
    }

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false, insertable=false, updatable=false)
    public ProjectTableEntity getProjectTableByProjectId() {
        return projectTableByProjectId;
    }

    public void setProjectTableByProjectId(ProjectTableEntity projectTableByProjectId) {
        this.projectTableByProjectId = projectTableByProjectId;
    }
}
