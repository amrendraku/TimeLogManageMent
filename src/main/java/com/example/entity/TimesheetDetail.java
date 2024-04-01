package com.example.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "timesheet_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TimesheetDetail {
	
    @Id
    @Column(name = "timesheet_detail_id")
    private Long timesheetDetailId;

    @Column(name = "timesheet_detail_month", nullable = false)
    private String timesheetDetailMonth;

    @Column(name = "template_name_id", nullable = false)
    private Long templateNameId;

    @ManyToOne
	@JoinColumn(name = "resource_id", nullable = true)
	private Resource resource;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "billable_status", nullable = false)
    private Long billableStatus;

    @Column(name = "billable_hour", nullable = false)
    private Long billableHour;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updation_date")
    private Date updationDate;

    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "is_active", nullable = false)
    private Long isActive;

    // Constructors, getters, and setters
}
