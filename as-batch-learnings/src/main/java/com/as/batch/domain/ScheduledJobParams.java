package com.as.batch.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "SCH_JOB_PARAMS")
@EqualsAndHashCode(of = { "name" })
public class ScheduledJobParams {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = ScheduledJob.class)
	@JoinColumn(name = "SCH_JOB_ID", updatable = false)
	private ScheduledJob scheduledJob;
	private String name;
	private String value;

	public ScheduledJobParams() {

	}

	private ScheduledJobParams(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public static final ScheduledJobParams of(String name, String value) {
		return new ScheduledJobParams(name, value);
	}

}
