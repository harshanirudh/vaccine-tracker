package com.harsha.tracker.vaccinetracker.exceptions;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.harsha.tracker.vaccinetracker.model.ErrorResponse;

@ControllerAdvice
public class GloablExceptionHandler {
	private Logger log = LogManager.getLogger(GloablExceptionHandler.class);
	
	@ExceptionHandler(value=TaskNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleTaskException(TaskNotFoundException e){
		log.error(e.getMessage(),e);
		ErrorResponse response= new ErrorResponse("500", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(value=IncorrectArgumentsForSubscriptionException.class)
	public ResponseEntity<ErrorResponse> handleIncorrectArgumentsForSubscriptionException(IncorrectArgumentsForSubscriptionException e){
		log.error(e.getMessage(),e);
		ErrorResponse response= new ErrorResponse("400", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=UnableToUnScubscribeException.class)
	public ResponseEntity<ErrorResponse> handleUnableToUnScubscribeExceptionException(UnableToUnScubscribeException e){
		log.error(e.getMessage(),e);
		ErrorResponse response= new ErrorResponse("400", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value=SQLException.class)
	public ResponseEntity<ErrorResponse> handleSqlException(SQLException e){
		log.error(e.getMessage(),e);
		ErrorResponse response= new ErrorResponse("500", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value=NullPointerException.class)
	public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e){
		log.error(e.getMessage(),e);
		ErrorResponse response= new ErrorResponse("500", e.getMessage());
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
