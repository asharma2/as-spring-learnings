package com.as.batch.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class BaseEntity {

	private boolean active;
	private LocalDateTime modifiedTs;
	private LocalDateTime createdTs;
}
