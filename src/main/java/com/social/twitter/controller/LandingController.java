package com.social.twitter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LandingController {

	@RequestMapping("/")
	private String landing(){
		return "MutualFriendViewer";
	}
}
