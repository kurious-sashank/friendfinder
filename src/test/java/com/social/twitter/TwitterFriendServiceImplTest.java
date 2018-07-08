package com.social.twitter;

import com.social.twitter.client.ITwitterApiClient;
import com.social.twitter.exception.TwitterUserNotFoundException;
import com.social.twitter.service.impl.TwitterFriendServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.social.twitter.api.TwitterProfile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TwitterFriendServiceImplTest {
	@Mock
	private ITwitterApiClient twitterApiClient;
	@InjectMocks
	private TwitterFriendServiceImpl twitterFriendService;

	@Test
	public void findMutualFriends_withMutualFriends_returnsCommonIds() throws TwitterUserNotFoundException {
		//given
		String screenName1 ="someName1";
		String screenName2 ="someName2";
		List<Long> friends1 = new ArrayList<>(Arrays.asList(1L,2L,3L));
		List<Long> friends2 = new ArrayList<>(Arrays.asList(2L,3L));
		//when
		Mockito.when(twitterApiClient.getFriendIds(screenName1)).thenReturn(friends1);
		Mockito.when(twitterApiClient.getFriendIds(screenName2)).thenReturn(friends2);
		//then
		Assert.assertEquals(2,twitterFriendService.findMutualFriendIds(screenName1,screenName2).size());
	}

	@Test
	public void findMutualFriends_noMutualFriends_returnsNoCommonIds() throws TwitterUserNotFoundException {
		//given
		String screenName1 ="someName1";
		String screenName2 ="someName2";
		List<Long> friends1 = new ArrayList<>(Arrays.asList(1L,2L,3L));
		List<Long> friends2 = new ArrayList<>(Arrays.asList(5L,6L));
		//when
		Mockito.when(twitterApiClient.getFriendIds(screenName1)).thenReturn(friends1);
		Mockito.when(twitterApiClient.getFriendIds(screenName2)).thenReturn(friends2);
		//then
		Assert.assertEquals(0,twitterFriendService.findMutualFriendIds(screenName1,screenName2).size());
	}

	@Test(expected = TwitterUserNotFoundException.class)
	public void findMutualFriends_noMatchingId_throwsException() throws TwitterUserNotFoundException {
		//given
		String screenName1 ="someName1";
		String screenName2 ="someName2";
		List<Long> friends1 = new ArrayList<>(Arrays.asList(1L,2L,3L));
		List<Long> friends2 = new ArrayList<>(Arrays.asList(5L,6L));
		//when
		Mockito.when(twitterApiClient.getFriendIds(screenName1)).thenThrow(TwitterUserNotFoundException.class);
		Mockito.when(twitterApiClient.getFriendIds(screenName2)).thenReturn(friends2);
		//then
		twitterFriendService.findMutualFriendIds(screenName1,screenName2);
	}
}

