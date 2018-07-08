package com.social.twitter.service;

import com.social.twitter.exception.TwitterUserNotFoundException;

import java.util.List;

public interface ITwitterFriendService extends ITwitterService {
	public List<Long> findMutualFriendIds(String screenName1, String screenName2) throws TwitterUserNotFoundException;
}
