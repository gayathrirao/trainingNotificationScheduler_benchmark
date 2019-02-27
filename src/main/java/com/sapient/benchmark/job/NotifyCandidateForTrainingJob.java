package com.sapient.benchmark.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.sapient.benchmark.service.CandidateForTrainingService;

public class NotifyCandidateForTrainingJob  implements Job{

	@Autowired
	CandidateForTrainingService candidateForTrainingService;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		candidateForTrainingService.execute();
		
	}

}
