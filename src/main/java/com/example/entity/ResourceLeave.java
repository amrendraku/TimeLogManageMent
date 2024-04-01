package com.example.entity;

import java.util.Date;
import java.util.Optional;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resourceLeave")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceLeave {
	
	@Id
	@Column(name = "resource_leave_id")
	private Long resourceLeaveId;

	@Column(name = "leave_month", nullable = false)
	private String leaveMonth;

	@Column(name = "leave_type", nullable = false)
	private String leaveType;

	@ManyToOne
	@JoinColumn(name = "resource_id", nullable = false)
	private Resource resource;

	@Column(name = "no_of_days", nullable = false)
	private Long noOfDays;

	@Column(name = "leave_start_date", nullable = false)
	private Date leaveStartDate;

	@Column(name = "leave_end_date", nullable = false)
	private Date leaveEndDate;

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

}
