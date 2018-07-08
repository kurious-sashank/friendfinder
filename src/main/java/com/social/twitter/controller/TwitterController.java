package com.social.twitter.controller;

import com.social.twitter.exception.TwitterUserNotFoundException;
import com.social.twitter.service.ITwitterFriendService;
import com.social.twitter.service.ITwitterUserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(TwitterController.TWITTER_BASE_API)
public class TwitterController {

	public static final String TWITTER_BASE_API="service";
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);

	private Twitter twitter;
	private ITwitterFriendService twitterFriendService;
	private ITwitterUserProfileService userProfileService;

	@Autowired
	public TwitterController(Twitter twitter, @Qualifier("twitterFriendService") ITwitterFriendService twitterFriendService, @Qualifier("userProfileService") ITwitterUserProfileService userProfileService){
		this.twitter = twitter;
		this.twitterFriendService = twitterFriendService;
		this.userProfileService = userProfileService;
	}

	@RequestMapping(value="/findmutualfriends", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getMutualFriends(@RequestParam String screenName1, @RequestParam String screenName2, Model model) throws TwitterUserNotFoundException {
		LOGGER.info("Finding mutual friends for users {} & {}",screenName1, screenName2);
		List<Long> commonUserIds =  twitterFriendService.findMutualFriendIds(screenName1,screenName2);
		return userProfileService.fetchUserProfileUrls(commonUserIds);
	}

}
