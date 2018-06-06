package by.bsu.rfe.smsservice.scheduler;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.annotation.PostConstruct;

/**
 * Created by pluhin on 1/6/17.
 */
@Component
public class SmsServerScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServerScheduler.class);

    @Autowired
    private List<SmsServerComponent> components;

    @PostConstruct
    public void startComponents() throws SchedulerException {
        LOGGER.info("STARTING COMPONENTS");

        for (SmsServerComponent component : components) {
            component.startJob();
        }

        LOGGER.info("ALL COMPONENTS STARTED");
    }

}
