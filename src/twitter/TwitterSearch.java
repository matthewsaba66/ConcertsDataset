package twitter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import model.Event;
import model.Location;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearch {




	public static void main(String[] args) throws TwitterException {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("KGQrZaAdz02QteFInwZyS9bj0")
		.setOAuthConsumerSecret("D6do3Wz7kZTfr1zZxHQZwLYkAtppC2oaiR3Lo0cC7v0Mj2RXjU")
		.setOAuthAccessToken("133707504-DnJ3TatNImS1bGBptA6OzwA90cTyo6UfS5gPfqpb")
		.setOAuthAccessTokenSecret("cKbV91YlLLP4wttBdVimCnyjkqN2dFbK0lsILMF583Fiu");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		// The factory instance is re-useable and thread safe.
		//Twitter twitter = TwitterFactory.getSingleton();
		String itaCity = "Rome";
		String city;
		switch (itaCity){
		case "Rome": city = "Roma"; break;
		case "Milan": city = "Milano"; break;
		case "Turin": city = "Torino"; break;
		case "Naples": city = "Napoli";	break;
		case "Florence": city = "Firenze"; break;
		case "Bari": city = "Bari"; break;
		case "Verona": city = "Verona"; break;
		default: city = itaCity;
		}
		System.out.println(city);
		Query query = new Query("muse milano");
		//query.count(12);
		//query.setCount(2);
		query.setSince("2015-12-27");
		query.setUntil("2015-12-28");
		QueryResult result = twitter.search(query);
		System.out.println(result.getTweets().size());
			for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText() + status.getCreatedAt());
	}
		 
		Date first = result.getTweets().get(0).getCreatedAt();
		Date last = result.getTweets().get(result.getTweets().size()-1).getCreatedAt();
		System.out.println(first.getTime());
		System.out.println(last.getTime());

		double delta = ((first.getTime() - last.getTime()));
		double deltaH = delta/3600000;
		System.out.println(delta);
		System.out.println(deltaH);

		double deltaHN = normalize(deltaH);
		System.out.println(deltaHN);

	}

	public static double getLast10Density(Event event, Location venue) throws TwitterException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("KGQrZaAdz02QteFInwZyS9bj0")
		.setOAuthConsumerSecret("D6do3Wz7kZTfr1zZxHQZwLYkAtppC2oaiR3Lo0cC7v0Mj2RXjU")
		.setOAuthAccessToken("133707504-DnJ3TatNImS1bGBptA6OzwA90cTyo6UfS5gPfqpb")
		.setOAuthAccessTokenSecret("cKbV91YlLLP4wttBdVimCnyjkqN2dFbK0lsILMF583Fiu");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		// The factory instance is re-useable and thread safe.
		//Twitter twitter = TwitterFactory.getSingleton();
		String city;
		switch (venue.getCity()){
		case "Rome": city = "Roma"; break;
		case "Milan": city = "Milano"; break;
		case "Turin": city = "Torino"; break;
		case "Naples": city = "Napoli";	break;
		case "Florence": city = "Firenze"; break;
		case "Bari": city = "Bari"; break;
		case "Verona": city = "Verona"; break;
		default: city = venue.getCity();
		}
		Query query = new Query(event.getArtist() + " " + city);
		query.setCount(10);
		QueryResult result = twitter.search(query);
		//System.out.println(result.getTweets().size());
		/*	for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText() + status.getCreatedAt());
	}
		 */
		if(result.getTweets().isEmpty())
			return 0;
		Date first = result.getTweets().get(0).getCreatedAt();
		Date last = result.getTweets().get(result.getTweets().size()-1).getCreatedAt();
		double delta = ((first.getTime() - last.getTime()));
		double deltaH = delta/3600000;
		double deltaHN = normalize(deltaH);
		return deltaHN;
	}


	public static double normalize(double number){
		//popularity = popularity.replace(',', '.');
		//Double number = Double.parseDouble(popularity);
		NumberFormat formatter = new DecimalFormat("#.000");
		formatter.setMaximumFractionDigits(3);
		String form = formatter.format(number);
		form =  form.replace(',', '.');
		return Double.parseDouble(form);
	}


}
