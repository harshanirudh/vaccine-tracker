package com.harsha.tracker.vaccinetracker.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harsha.tracker.vaccinetracker.dao.SubscriptionDao;
import com.harsha.tracker.vaccinetracker.exceptions.IncorrectArgumentsForSubscriptionException;
import com.harsha.tracker.vaccinetracker.exceptions.UnableToUnScubscribeException;
import com.harsha.tracker.vaccinetracker.model.Success;

@RestController
@RequestMapping("/v1/subscription")
public class SubscriptionController {
	private Logger log = LogManager.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionDao dao;

	@GetMapping("/add")
	public ResponseEntity<Success> subscribeEmail(@RequestParam String email, @RequestParam Integer districtId,
			@RequestParam String vaccine) throws IncorrectArgumentsForSubscriptionException {
		int res = dao.addNewSubscription(email, districtId, vaccine);
		Success response= new Success();
		if (res == -1) {
			StringBuilder errorMessageBuilder = new StringBuilder();
			errorMessageBuilder.append("Arguments provided,Email:").append(email).append(",District ID:")
					.append(districtId).append(",Vaccine:").append(vaccine).append(" Are incorrect for subscription");
			throw new IncorrectArgumentsForSubscriptionException(errorMessageBuilder.toString());
		}
		else if(res== 1 || res==3) {
			response.setMessage("Subscribed Succesfully");
		}
		else if (res==2) {
			response.setMessage("Email Already Subscribed");
		}
		response.setStatus("201");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<Success> unsubscribeEmail(@RequestParam String email, @RequestParam int districtId,
			@RequestParam String vaccine) throws UnableToUnScubscribeException{
		int res=dao.deleteSubscription(email, districtId, vaccine);
		if(res<1) {
			throw new UnableToUnScubscribeException("Invalid input for deleting subscription");
		}
		Success response = new Success("200",null,"Successfully Unsubscribed");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
