package com.harsha.tracker.vaccinetracker.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.stereotype.Service;

import com.harsha.tracker.vaccinetracker.exceptions.TaskNotFoundException;

@Service
public class VaccineAlertScheduler {

//	private static final long SLEEP_MILLIS = 300*1000;;
	@Autowired
	private AvailabiltyService service;
	@Autowired
	private TaskScheduler scheduler;
	private static  boolean isEmailSent = false;
	private Map<Integer,ScheduledFuture<?>> jobMap= new HashMap<>();
	private Logger log = LogManager.getLogger(VaccineAlertScheduler.class);
	
		public void addTask(int jobId,int districtId,String vaccine) {
			Runnable task= new Runnable() {
				
				@Override
				public void run() {
					ZoneId zone=ZoneId.of("Asia/Kolkata");
					String date=ZonedDateTime.now(zone).format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
					isEmailSent = service.sendAlertAvailableAppointmentsByDistrictByWeek(districtId, date, vaccine, jobId);
					log.debug("EMAIL SENT for: "+districtId+" ,"+isEmailSent);
				}
			};
			Trigger trigger=new Trigger() {
				
				@Override
				public Date nextExecutionTime(TriggerContext triggerContext) {
					Instant nextInstant =null;
					Date lastCompletionTime = triggerContext.lastCompletionTime();
					if(lastCompletionTime==null)
						lastCompletionTime=Date.from(Instant.now());
					if(isEmailSent)
						 nextInstant = lastCompletionTime.toInstant().plusSeconds(300);
					else
						nextInstant = lastCompletionTime.toInstant().plusSeconds(30);
					Date nextDate=Date.from(nextInstant);
					return nextDate;
				}
			};
			ScheduledFuture<?> future=scheduler.schedule(task, trigger);
			jobMap.put(jobId, future);
			log.info("Added job ID:"+jobId);
		}
		
		public boolean removeTask(int id) throws TaskNotFoundException {
			ScheduledFuture<?> job = jobMap.get(id);
			if(job==null)
				throw new TaskNotFoundException();
			ScheduledFuture<?> task = job;
			boolean cancel = task.cancel(true);
			if(cancel) {
				jobMap.remove(id);
				log.info("Removed job ID:"+id);
			}
			return cancel;
		}

		public Map<Integer, ScheduledFuture<?>> getJobMap() {
			return jobMap;
		}
		
}

//	@Scheduled(fixedRate = 30000)
//	public void alert(int districtId,String vaccine,String recipients) throws InterruptedException {
//		LocalDate now = LocalDate.now();
//		String date1 = now.format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
//		String date2 = now.plusDays((long)7).format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
//			if (service.sendAlertAvailableAppointmentsByDistrictByWeek(districtId, date1, vaccine,recipients) || service.sendAlertAvailableAppointmentsByDistrictByWeek(districtId, date2, vaccine,recipients)) {
//				log.info(" Going to sleep");
//				Thread.sleep(SLEEP_MILLIS);
//				log.info(" back from sleep");
//		}
//
//	}