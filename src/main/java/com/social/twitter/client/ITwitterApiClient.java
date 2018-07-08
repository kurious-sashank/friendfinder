package com.social.twitter.client;

import com.social.twitter.exception.TwitterUserNotFoundException;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.List;

public interface ITwitterApiClient {
	public List<Long> getFriendIds (String screenName) throws TwitterUserNotFoundException;
	public List<TwitterProfile> getTwitterProfiles(List<Long> userIds)throws TwitterUserNotFoundException;
}
