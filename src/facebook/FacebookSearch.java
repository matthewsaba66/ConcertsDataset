package facebook;

import facebook4j.Account;
import facebook4j.Comment;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.auth.OAuthSupport;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import model.Location;

/*
 *  1 - search for events that match the given query
 *  
 *  2 - aggregates the attendings of all events found for the given query
 *  
 */
public class FacebookSearch {

	private static final String appId = "893037237460771";
	private static final String appSecret = "df1bb1cb93e262dc2c309f5bfbcc91ce";
	private static final String commaSeparetedPermissions = "email,publish_stream";



	public static void main(String[] args) throws FacebookException {

		// Generate facebook instance.
		Facebook facebook = new FacebookFactory().getInstance();
		// Use default values for oauth app id.
		facebook.setOAuthAppId("", "");
		// Get an access token from: 
		// https://developers.facebook.com/tools/explorer
		// Copy and paste it below.
		String accessTokenString = "CAAMsNm9PXyMBAMxYGp0xq8B5VK0ktoOOquGAXB2RGinuB4VsEJJOmpycdEKKKuTe1fEeV6mygWZC786NW7fp1tdFlI55NB3HZBZAI3pfF80igKcJ6Apz8V3mwHg47kwQpOTJy4YSD6ZBVaSbeEwImzvZBr4wYtnORgP2lzpmEKAz2VTgmmjel4bUjcaYFh6cZD";
		AccessToken at = new AccessToken(accessTokenString);
		// Set access token.
		facebook.setOAuthAccessToken(at);

		// We're done.
		// Access group feeds.
		// You can get the group ID from:
		// https://developers.facebook.com/tools/explorer


		ResponseList<Event> results = facebook.searchEvents("muse mediolanum forum");
		int attending = 0;
		for (Event event : results){
			System.out.println(event.getName());
			//	    	System.out.println(facebook.getRSVPStatusAsInvited(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInAttending(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInMaybe(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInDeclined(event.getId()).size());
			attending = attending + facebook.getRSVPStatusInAttending(event.getId()).size();

		}
		System.out.println("Total attending: " + attending);
	}




	public static void getFBAttendings(model.Event event, Location venue) throws FacebookException{
		// Generate facebook instance.
		Facebook facebook = new FacebookFactory().getInstance();
		// Use default values for oauth app id.
		facebook.setOAuthAppId("", "");
		// Get an access token from: 
		// https://developers.facebook.com/tools/explorer
		// Copy and paste it below.
		String accessTokenString = "CAAMsNm9PXyMBAMxYGp0xq8B5VK0ktoOOquGAXB2RGinuB4VsEJJOmpy"
				+ "cdEKKKuTe1fEeV6mygWZC786NW7fp1tdFlI55NB3HZBZAI3pfF80igKcJ6Apz8V3mwHg47kwQ"
				+ "pOTJy4YSD6ZBVaSbeEwImzvZBr4wYtnORgP2lzpmEKAz2VTgmmjel4bUjcaYFh6cZD";
		AccessToken at = new AccessToken(accessTokenString);
		// Set access token.
		facebook.setOAuthAccessToken(at);

		// We're done.
		// Access group feeds.
		// You can get the group ID from:
		// https://developers.facebook.com/tools/explorer

		String query = event.getArtist() + " " + venue.getPlace();
		ResponseList<Event> results = facebook.searchEvents(query);
		int attending = 0;
		for (Event e : results){
			//System.out.println(e.getName());
			//	    	System.out.println(facebook.getRSVPStatusAsInvited(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInAttending(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInMaybe(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInDeclined(event.getId()).size());
			attending = attending + facebook.getRSVPStatusInAttending(e.getId()).size();

		}
		System.out.println("Total attending for string '"+ query +"': " + attending + "\n");
	}


	public static int getFBAttendingsInt(model.Event event, Location venue) throws FacebookException{
		// Generate facebook instance.
		Facebook facebook = new FacebookFactory().getInstance();
		// Use default values for oauth app id.
		facebook.setOAuthAppId("", "");
		// Get an access token from: 
		// https://developers.facebook.com/tools/explorer
		// Copy and paste it below.
		String accessTokenString = "CAAMsNm9PXyMBAMxYGp0xq8B5VK0ktoOOquGAXB2RGinuB4VsEJJOmpy"
				+ "cdEKKKuTe1fEeV6mygWZC786NW7fp1tdFlI55NB3HZBZAI3pfF80igKcJ6Apz8V3mwHg47kwQ"
				+ "pOTJy4YSD6ZBVaSbeEwImzvZBr4wYtnORgP2lzpmEKAz2VTgmmjel4bUjcaYFh6cZD";
		AccessToken at = new AccessToken(accessTokenString);
		// Set access token.
		facebook.setOAuthAccessToken(at);

		// We're done.
		// Access group feeds.
		// You can get the group ID from:
		// https://developers.facebook.com/tools/explorer

		String query = event.getArtist() + " " + venue.getPlace();
		ResponseList<Event> results = facebook.searchEvents(query);
		int attending = 0;
		for (Event e : results){
			//System.out.println(e.getName());
			//	    	System.out.println(facebook.getRSVPStatusAsInvited(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInAttending(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInMaybe(event.getId()).size());
			//	    	System.out.println(facebook.getRSVPStatusInDeclined(event.getId()).size());
			//while(true){
				try{
					attending = attending + facebook.getRSVPStatusInAttending(e.getId()).size();
				}
				catch (FacebookException exception){
					System.out.println("mega errore");
				}
			}

		//}
		return attending;
	}
}

