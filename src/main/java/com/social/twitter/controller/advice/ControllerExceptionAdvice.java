package com.social.twitter.controller.advice;

import com.social.twitter.exception.TwitterUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.social.twitter.controller")
public class ControllerExceptionAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception ex){
		return new ResponseEntity<String>(ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
