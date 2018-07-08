package com.social.twitter.service;

import com.social.twitter.exception.TwitterUserNotFoundException;

import java.util.List;

public interface ITwitterUserProfileService extends ITwitterService {
	public List<String> fetchUserProfileUrls(List<Long> userIds) throws TwitterUserNotFoundException;
}
