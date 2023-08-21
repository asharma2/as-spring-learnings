package com.as.batch.generator;

import java.util.UUID;

import org.quartz.SchedulerException;
import org.quartz.spi.InstanceIdGenerator;

public class QrtzJobIdGenerator implements InstanceIdGenerator {

    @Override
    public String generateInstanceId() throws SchedulerException {
        try {
            return UUID.randomUUID().toString();
        } catch (Exception e) {
            throw new SchedulerException("Quartz ID generation failed", e);
        }
    }

}
