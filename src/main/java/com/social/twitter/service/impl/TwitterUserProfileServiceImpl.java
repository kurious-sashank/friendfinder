package com.social.twitter.service.impl;

import com.social.twitter.client.ITwitterApiClient;
import com.social.twitter.exception.TwitterUserNotFoundException;
import com.social.twitter.service.ITwitterUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userProfileService")
public class TwitterUserProfileServiceImpl implements ITwitterUserProfileService {
	private ITwitterApiClient twitterApiClient;

	@Autowired
	public TwitterUserProfileServiceImpl(@Qualifier("userOperationsClient") ITwitterApiClient twitterApiClient){
		this.twitterApiClient = twitterApiClient;
	}
	public List<String> fetchUserProfileUrls(List<Long> userIds) throws TwitterUserNotFoundException {
		List<TwitterProfile> friends = twitterApiClient.getTwitterProfiles(userIds);
		return friends.stream().map(friend -> friend.getProfileUrl()).collect(Collectors.toList());
	}
}
