package com.social.twitter.exception;

public class TwitterUserNotFoundException extends Exception {

	public  TwitterUserNotFoundException(String message, Exception ex){
		super(message, ex);
	}
}
