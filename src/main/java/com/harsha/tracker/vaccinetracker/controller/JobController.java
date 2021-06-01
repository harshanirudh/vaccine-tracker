package com.harsha.tracker.vaccinetracker.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.tracker.vaccinetracker.dao.JobDetailsDao;
import com.harsha.tracker.vaccinetracker.exceptions.TaskNotFoundException;
import com.harsha.tracker.vaccinetracker.model.JobDetails;
import com.harsha.tracker.vaccinetracker.services.VaccineAlertScheduler;

@RestController
@RequestMapping("/v1/jobs")
public class JobController {
	@Autowired
	private VaccineAlertScheduler scheduler;
	@Autowired
	private JobDetailsDao jobdao;
	
	private Logger log = LogManager.getLogger(JobController.class);
	
	@GetMapping("/addAll")
	public ResponseEntity<String> addAllJobsForScheduling(){
		List<JobDetails> list = jobdao.findAllJobDetails();
		list.forEach(job ->{ 
			scheduler.addTask(job.getJOB_ID(), job.getDISTRICT_ID(), job.getVACCINE_TYPE());
		});
		return new ResponseEntity<>("Successfully added all",HttpStatus.OK);
	}
	@GetMapping("/removeAll")
	public ResponseEntity<String> removeAllJobsForScheduling() throws TaskNotFoundException{
		Integer[] set = scheduler.getJobMap().keySet().stream().toArray(Integer[]::new);
		for(int i=0; i<set.length;i++)
			scheduler.removeTask(set[i]);
		return new ResponseEntity<>("Successfully removed all",HttpStatus.OK);
	}
	
	@GetMapping("/running")
	public ResponseEntity<Integer[]> getAllRunningJobs(){
		 Integer[] res = scheduler.getJobMap().keySet().stream().toArray(Integer[]::new);
		 return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
