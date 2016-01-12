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
import model.Event;
import model.Location;
import model.Measurement;
import twitter.TwitterSearch;
import twitter4j.TwitterException;

public class MeasurementsPicker {

	public static void main(String[] args) throws SQLException, FacebookException, TwitterException, ParseException, NumberFormatException, IOException{
		EventDAOImpl daoEvents = new EventDAOImpl();
		LocationDAOImpl daoLocation = new LocationDAOImpl();
		List<Event>eventList = daoEvents.getEvents();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		//String date = dateFormat.format(new Date());
		Date today = new Date(dateFormat.format(new Date()));

		MeasurementDAOImpl daoMeas = new MeasurementDAOImpl();
		GoogleSearcher googleSearcher = new GoogleSearcher();
		int i = 1;
		for (Event event : eventList){
			String eventDate = event.getDate().replaceAll("-", "/");
			if (dateFormat.parse(eventDate).compareTo(today) == 0 || dateFormat.parse(eventDate).compareTo(today) == 1){
				if (i%175==0){
					//wait 16 minutes
					try {
						Thread.sleep(1000*60*15);                 //1000 milliseconds is one second.
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
				
				//google
				long google = googleSearcher.getFoundPages(event, venue);

				Measurement measurement = new Measurement(dateFormat.format(new Date()), event.getId(),
						bing_web, bing_news, facebook, twitter, 0, 0, google);
				System.out.println(measurement.toString());
				daoMeas.insertMeasurement(measurement);

				i++;

				//System.out.println("accetto: " + event.getDate() + " " + eventDate +"		" + dateFormat.parse(eventDate).compareTo(today) );
			}
			else {
				//System.out.println("rifiuto: " + event.getDate() + " " + eventDate + "		" + dateFormat.parse(eventDate).compareTo(today) );
				i++;
			}
		}
	}

}
