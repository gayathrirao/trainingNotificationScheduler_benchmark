package com.sapient.benchmark;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan
@EnableScheduling
public class TrainingNotificationSchedulerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TrainingNotificationSchedulerApplication.class).bannerMode(Mode.OFF).run(args);
	}

}
