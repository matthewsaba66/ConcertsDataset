package main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import bing.BingSearch;
import dao.EventDAOImpl;
import dao.LocationDAOImpl;
import dao.MeasurementDAOImpl;
import facebook.FacebookSearch;
import facebook4j.FacebookException;
import google.GoogleSearcher;
import google.GoogleSearcherNews;
import model.Event;
import model.Location;
import model.Measurement;
import twitter.TwitterSearch;
import twitter4j.TwitterException;

public class MeasurementsGetter {

	public static void main(String[] args) throws SQLException, FacebookException, TwitterException, ParseException, NumberFormatException, IOException{
		EventDAOImpl daoEvents = new EventDAOImpl();
		LocationDAOImpl daoLocation = new LocationDAOImpl();
		List<Event>eventList = daoEvents.getEvents();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		//String date = dateFormat.format(new Date());
		Date today = new Date(dateFormat.format(new Date()));

		MeasurementDAOImpl daoMeas = new MeasurementDAOImpl();
		GoogleSearcher googleSearcher = new GoogleSearcher();
		GoogleSearcherNews googleSearcherNews = new GoogleSearcherNews();
		int i = 1;
		System.out.println(eventList.size());
		for (Event event : eventList){//*/for (int i = 279; i < eventList.size(); i++){ Event event = eventList.get(i);
			String eventDate = event.getDate().replaceAll("-", "/");
			if (dateFormat.parse(eventDate).compareTo(today) == 0 || dateFormat.parse(eventDate).compareTo(today) == 1){
				if (i%140==0){
					//wait 16 minutes
					try {
						Thread.sleep(1000*60*16);                 //1000 milliseconds is one second.
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
				System.out.println(event.toString());
				Location venue = daoLocation.getLocation(event.getLocation());
				System.out.println(i);
				//bing 
				int[] bing = BingSearch.getNewsInt(event, venue);
				int bing_web = bing[0];
				int bing_news = bing[1];

				//facebook
				int facebook = FacebookSearch.getFBAttendingsInt(event, venue);

				//twitter
				double twitter = TwitterSearch.getLast10Density(event, venue);

				//google pages
				long googlePages = googleSearcher.getFoundPages(event, venue);

				//google news
				long googleNews = googleSearcherNews.getFoundPages(event, venue);

				Measurement measurement = new Measurement(dateFormat.format(new Date()), event.getId(),
						bing_web, bing_news, facebook, twitter, 0, 0, googlePages, googleNews);
				System.out.println(measurement.toString());
				daoMeas.insertMeasurement(measurement);

				i++;
			}
			else {
				i++;
			}
		}
	}

}
