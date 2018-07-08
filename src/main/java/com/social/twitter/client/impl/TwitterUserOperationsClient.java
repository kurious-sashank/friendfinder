package com.social.twitter.client.impl;

import com.social.twitter.client.ITwitterApiClient;
import com.social.twitter.controller.TwitterController;
import com.social.twitter.exception.TwitterUserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component("userOperationsClient")
public class TwitterUserOperationsClient implements ITwitterApiClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterController.class);
	@Autowired
	private Twitter twitter;

	public List<Long> getFriendIds (String screenName) throws TwitterUserNotFoundException{
		LOGGER.debug("Finding friends for {}",screenName);
		try {
			CursoredList<Long> friends = twitter.friendOperations().getFriendIds(screenName);
			List<Long> friendIds = new ArrayList<>(friends.size());
			for (int i = 0; i < friends.size(); i++) {
				friendIds.add(friends.get(i));
			}
			return friendIds;
		}catch(ResourceNotFoundException ex){
			String message= "No user with the screen name : "+screenName+" was found";
			LOGGER.error(message, ex);
			throw new TwitterUserNotFoundException( message, ex);
		}
	}

	public List<TwitterProfile> getTwitterProfiles(List<Long> userIds)throws TwitterUserNotFoundException{
		long[] userIdArr = new long[50];
		List<TwitterProfile> friends = new ArrayList<>();
		//Requests won't go in larger than batches of 100
		for(int i=0; i<userIds.size(); i++) {
			int arrPos = (i%userIdArr.length);
			userIdArr[arrPos] = userIds.get(i);
			if((arrPos==0 && i>0)||i==userIds.size()-1){
				try{
				friends.addAll(twitter.userOperations().getUsers(userIdArr));
				}catch(ResourceNotFoundException ex){
					LOGGER.error("Error occurred when fetching user profiles on {}", Arrays.toString(userIdArr));
					String message= "Error occurred when fetching user profiles";
					throw new TwitterUserNotFoundException( message, ex);
				}
			}

		}
		return friends;
	}
}
