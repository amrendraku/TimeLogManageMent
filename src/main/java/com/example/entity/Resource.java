package com.example.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

	@Id
	@Column(name = "resource_id")
	private Long resourceId;

	@Column(name = "resource_name", nullable = false)
	private String resourceName;

	@Column(name = "resource_type", nullable = false)
	private String resourceType;

	@Column(name = "resource_designation", nullable = false)
	private String resourceDesignation;

	@Column(name = "resource_joining_date", nullable = false)
	private Date resourceJoiningDate;

	@Column(name = "resource_separation_date")
	private Date resourceSeparationDate;

	@Column(name = "resource_manager_name", nullable = false)
	private String resourceManagerName;

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
