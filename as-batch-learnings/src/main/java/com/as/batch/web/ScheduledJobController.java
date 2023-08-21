package com.as.batch.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.as.batch.domain.ScheduledJob;
import com.as.batch.dto.ScheduledJobDTO;
import com.as.batch.mapper.DTOMapper;
import com.as.batch.mapper.DomainMapper;
import com.as.batch.service.SchedulerService;

@RestController
@RequestMapping("/api/v1/scheduledjob")
public class ScheduledJobController {

	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	private DomainMapper domainMapper;

	@Autowired
	private DTOMapper dtoMapper;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody ScheduledJobDTO dto) {
		ScheduledJob job = domainMapper.mapScheduledJob(dto);
		schedulerService.saveAndSchedule(job);
		ScheduledJobDTO sDto = dtoMapper.mapScheduledJob(job);
		return ResponseEntity.ok(sDto);
	}

	@GetMapping("/name/{name}/group/{group}")
	public ResponseEntity<?> get(@PathVariable("name") String name, @PathVariable("group") String group) {
		Optional<ScheduledJob> optScheduledJob = schedulerService.findByNameAndGroup(name, group);
		if (optScheduledJob.isPresent()) {
			return ResponseEntity.ok(dtoMapper.mapScheduledJob(optScheduledJob.get()));
		}
		return ResponseEntity.noContent().build();
	}

}
