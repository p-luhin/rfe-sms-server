package by.bsu.rfe.smsservice.scheduler;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by pluhin on 1/6/17.
 */
public abstract class SmsServerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServerComponent.class);

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void startJob() throws SchedulerException {
        LOGGER.info("Starting job {}", getJobName());

        JobDetail jobDetail = JobBuilder.newJob(getJobClass()).withIdentity(getJobName()).build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(getTriggerName())
                .withSchedule(CronScheduleBuilder.cronSchedule(getCron()))
                .build();

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    protected abstract Class<? extends Job> getJobClass();
    protected abstract String getCron();
    protected abstract String getJobName();
    protected abstract String getTriggerName();
}
