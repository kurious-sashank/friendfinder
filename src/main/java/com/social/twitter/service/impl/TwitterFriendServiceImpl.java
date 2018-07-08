package com.social.twitter.service.impl;

import com.social.twitter.client.ITwitterApiClient;
import com.social.twitter.exception.TwitterUserNotFoundException;
import com.social.twitter.service.ITwitterFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("twitterFriendService")
public class TwitterFriendServiceImpl implements ITwitterFriendService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterFriendServiceImpl.class);
	private ITwitterApiClient twitterApiClient;

	@Autowired
	public TwitterFriendServiceImpl(@Qualifier("userOperationsClient") ITwitterApiClient twitterApiClient) {
		this.twitterApiClient = twitterApiClient;
	}

	public List<Long> findMutualFriendIds(String screenName1, String screenName2) throws TwitterUserNotFoundException {
		List<Long> screenName1FriendIds = twitterApiClient.getFriendIds(screenName1);
		List<Long> screenName2FriendIds = twitterApiClient.getFriendIds(screenName2);
		screenName2FriendIds.retainAll(screenName1FriendIds);
		LOGGER.info("{} mutual friends found for users {}, {}", screenName2FriendIds.size(),screenName1,screenName2);
		return screenName2FriendIds;
	}
}
