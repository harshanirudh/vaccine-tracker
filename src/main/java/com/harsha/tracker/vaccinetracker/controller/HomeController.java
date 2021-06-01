package com.harsha.tracker.vaccinetracker.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.tracker.vaccinetracker.services.EmailService;

@RestController
public class HomeController {
	@Autowired
	private EmailService email;

	@GetMapping("/")
	public String healthCheck() {
		return "Ok";
	}
}
