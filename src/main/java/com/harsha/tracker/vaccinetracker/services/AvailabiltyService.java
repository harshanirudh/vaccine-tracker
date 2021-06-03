package com.harsha.tracker.vaccinetracker.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsha.tracker.vaccinetracker.dao.SubscriptionDao;
import com.harsha.tracker.vaccinetracker.model.Centers;
import com.harsha.tracker.vaccinetracker.model.Centers.Center;
import com.harsha.tracker.vaccinetracker.model.Session;
import com.harsha.tracker.vaccinetracker.model.Subscription;
import com.sun.mail.imap.protocol.BODY;

@Service
//@ConditionalOnProperty(prefix ="application" ,name="ide",havingValue ="yes")
public class AvailabiltyService {

	private static final int SLEEP_MILLIS = 300 * 1000;

	@Autowired
	private MetaDataServices service;

	@Autowired
	private EmailService emailSender;

	@Autowired
	private SubscriptionDao subsDao;

	@Autowired
	private ObjectMapper mapper;

	public static boolean isHydEmailSent = false;
	public static boolean isRREmailSent = false;
	private ZoneId zone = ZoneId.of("Asia/Kolkata");
	private Logger log = LogManager.getLogger(AvailabiltyService.class);

	public boolean sendAlertAvailableAppointmentsByDistrictByWeek(int district_id, String date, String vaccine,
			int jobId) {
		boolean isEmailSent = false;
		try {
			if (log.isDebugEnabled())
				log.debug("Checking for date: " + date + ",for district: " + district_id + ", for vaccine: " + vaccine);
			Centers body = service.findAppointmentCalendarByDistrict(district_id, date).block();
			List<Center> res = body.getCenters().stream()
					.filter(center -> center.getSessions().stream()
							.anyMatch(session -> session.getAvailable_capacity() > 0
									&& session.getVaccine().toLowerCase().contains(vaccine.toLowerCase())))
					.collect(Collectors.toList());
			if (res.size() > 0) {
				String[] recipients = subsDao.findSubscriptionByJobId(jobId).stream().map(rec -> rec.getEMAIL())
						.toArray(String[]::new);
				if (recipients.length>0) {
					emailSender.sendMimeMessageBcc(recipients, subjectBuilder(vaccine).toString(), messageBuilder(res));
				}
				isEmailSent = true;
				log.info("EMAIL SENT for district id" + district_id);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return isEmailSent;
	}

	private StringBuilder subjectBuilder(String vaccine) {
		StringBuilder builder = new StringBuilder();
		builder.append("COVID-19 Availabiltly for vaccine:");
		builder.append(vaccine.isEmpty() ? "COVAXIN/COVISHILED" : vaccine);
		builder.append(" as of ");
		builder.append(ZonedDateTime.now(zone).format(DateTimeFormatter.ISO_DATE_TIME).toString());
		return builder;
	}

	private String messageBuilder(List<Center> result) {
		StringBuilder finalMessage = new StringBuilder();
		int size = result.size();
		for (int i = 0; i < size; i++) {
			Center currentObj = result.get(i);
			String center = currentObj.getName();
			String address = currentObj.getAddress() + "," + currentObj.getBlock_name() + ","
					+ currentObj.getDistrict_name() + "," + currentObj.getState_name() + "," + currentObj.getPincode();
			String fees = currentObj.getFee_type();
			List<Session> sessions = currentObj.getSessions();
			int sessionSize = sessions.size();
			String head = String
					.format("<table style=\"border: 1px solid black;border-collapse: collapse;\" rules=\"cols\">\r\n"
							+ "<thead>\r\n" + "<th colspan=%s> %s <br/>%s<br/>%s</th>\r\n" + "</thead>\r\n"
							+ "<tr style=\"border: 1px solid black;\" >", sessionSize, center, address, fees);
			finalMessage.append(head);
			for (int j = 0; j < sessionSize; j++) {
				String data = String.format(
						"<td> \r\n" + "	Date:%s<br/>\r\n" + "	%s<br/>\r\n" + "	Available:%d<br/>\r\n"
								+ "	Min Age:%d<br/>\r\n" + "	Slots:  %s\r\n" + "	</td>",
						sessions.get(j).getDate(), sessions.get(j).getVaccine(),
						sessions.get(j).getAvailable_capacity(), sessions.get(j).getMin_age_limit(),
						sessions.get(j).getSlots().toString());
				finalMessage.append(data);
			}
			finalMessage.append("</tr></table> <hr>");
		}
		return finalMessage.toString();
	}
}
