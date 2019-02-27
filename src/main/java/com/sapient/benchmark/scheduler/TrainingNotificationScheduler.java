package com.sapient.benchmark.scheduler;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

import com.sapient.benchmark.job.NotifyCandidateForTrainingJob;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@PropertySource("classpath:quartz.properties")
public class TrainingNotificationScheduler {

	@Value("${job.frequency}")
	private String frequency;
	
	@Autowired
    private ApplicationContext applicationContext;
	@Bean
	public JobDetailFactoryBean jobDetail() {
	    JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
	    jobDetailFactory.setName("Qrtz_Job_Detail");
	    jobDetailFactory.setJobClass(NotifyCandidateForTrainingJob.class);
	    jobDetailFactory.setDescription("Job to identify the suitable candidates for a training and notify them about it");
	    jobDetailFactory.setDurability(true);
	    return jobDetailFactory;
	}
	
	
	@Bean
	public SimpleTriggerFactoryBean trigger(JobDetail job) {
	    SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
	    trigger.setJobDetail(job);
	    log.info("Frequency " + frequency);
	    if(frequency == null) frequency = "3600";
	    trigger.setRepeatInterval(Integer.parseInt(frequency));
	    trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
	    trigger.setName("Qrtz_Trigger");
	    return trigger;
	}
	
	@Bean
	public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job) {
	    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
	    schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
	    log.info("Quartz properties set "+ schedulerFactory);
	    schedulerFactory.setJobFactory(springBeanJobFactory());
	    schedulerFactory.setJobDetails(job);
	    schedulerFactory.setTriggers(trigger);
	    return schedulerFactory;
	}
	

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        log.debug("Configuring Job factory");
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

}
