package com.harsha.tracker.vaccinetracker.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsha.tracker.vaccinetracker.model.Centers;
import com.harsha.tracker.vaccinetracker.model.Centers.Center;

@Service
//@ConditionalOnProperty(prefix ="application" ,name="ide",havingValue ="yes")
public class AvailabiltyService {

	@Autowired
	private MetaDataServices service;

	@Autowired
	private EmailService emailSender;

	@Autowired
	private ObjectMapper mapper;
	@Value(value = "${email.to}")
	private String recipients;
	
	public static boolean isHydEmailSent = false;
	public static boolean isRREmailSent = false;
	
	private Logger log = LogManager.getLogger(AvailabiltyService.class);
	
	public boolean sendAlertAvailableAppointmentsByDistrictByWeek(int district_id, String date, String vaccine,String recipients) {
		boolean isEmailSent = false;
		try {
			if(log.isDebugEnabled())
				log.debug("Checking for date: "+date+",for district: "+district_id+", for vaccine: "+vaccine);
			Centers body = service.findAppointmentCalendarByDistrict(district_id, date).block();
			List<Center> res = body.getCenters().stream()
					.filter(center -> center.getSessions().stream()
							.anyMatch(session -> session.getAvailable_capacity() >0
									&& session.getVaccine().toLowerCase().contains(vaccine)))
					.collect(Collectors.toList());
			if (res.size() > 0) {
				StringBuilder builder = new StringBuilder();
				builder.append("COVID-19 Availabiltly for vaccine:");
				builder.append(vaccine.isEmpty() ? "COVAXIN/COVISHILED" : vaccine);
				builder.append(" as of ");
				builder.append(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).toString());
				emailSender.sendSimpleMessage(recipients.split(","), builder.toString(),
						mapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
				isEmailSent = true;
				log.info("EMAIL SENT for district id" + district_id);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return isEmailSent;
	}

	@Scheduled(fixedRate = 30000)
	public void covaxinAlertHyd() {
		LocalDate now = LocalDate.now();
		String date1 = now.format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
		String date2 = now.plusDays((long)7).format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
		if (!isHydEmailSent) {
			if (sendAlertAvailableAppointmentsByDistrictByWeek(581, date1, "covaxin",recipients) || sendAlertAvailableAppointmentsByDistrictByWeek(581, date2, "covaxin",recipients))
				isHydEmailSent = true;
		}

	}

	@Scheduled(fixedRate = 30000)
	public void covaxinAlertRanga() {
		LocalDate now = LocalDate.now();
		String date1 = now.format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
		String date2 = now.plusDays((long)7).format(DateTimeFormatter.ofPattern("dd-MM-YYYY")).toString();
		if (!isRREmailSent) {
			if (sendAlertAvailableAppointmentsByDistrictByWeek(603, date1, "covaxin",recipients) || sendAlertAvailableAppointmentsByDistrictByWeek(603, date2, "covaxin",recipients))
				isRREmailSent = true;
		}
	}
}
