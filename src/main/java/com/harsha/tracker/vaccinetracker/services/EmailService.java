package com.harsha.tracker.vaccinetracker.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	public void sendSimpleMessage(String to[], String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendSimpleMessageBcc(String to[], String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setBcc(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public void sendMimeMessageBcc(String to[], String subject, String text) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper mimeMessage = new MimeMessageHelper(message, true);
		mimeMessage.setBcc(to);
		mimeMessage.setSubject(subject);
		message.setContent(text, "text/html");
		emailSender.send(message);
	}
}
