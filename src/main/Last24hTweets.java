package main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import dao.EventDAOImpl;
import dao.LocationDAOImpl;
import dao.MeasurementDAOImpl;
import model.Event;
import model.Location;
import model.Measurement;

public class Last24hTweets {

	private static MeasurementDAOImpl measurementDAOImpl = new MeasurementDAOImpl();
	private static EventDAOImpl eventDAOImpl = new EventDAOImpl();
	private static LocationDAOImpl locationDAOImpl = new LocationDAOImpl();

	public static void main(String[] args) throws SQLException, ParseException, IOException{
		List<Measurement> measurements = measurementDAOImpl.getMeasurements();
		//last24h(measurements);
		//last24hAuthorsOnly(measurements);
		//last24hCombined(measurements, "2016/01/02");
		last24hCombined(measurements, "2016/01/14");

		//test();
	}


	public static void test() throws SQLException, ParseException, IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		String date = dateFormat.format(new Date());
		Date today = dateFormat.parse(date);
		int i = 0;
		Measurement m = measurementDAOImpl.getMeasurement(764);
		Date measurementDate = dateFormat.parse(m.getDate()); 
		//solo se la data è minore di oggi
		if (measurementDate.before(today)){
			System.out.println("misurazione:" + m.toString());
			Event event = eventDAOImpl.getEvent(m.getEvent());
			Location location = locationDAOImpl.getLocation(event.getLocation());
			String dateSince = m.getDate().replaceAll("/", "-");
			Date since = dateFormat.parse(m.getDate());
			Calendar c = Calendar.getInstance();
			c.setTime(since);
			c.add(Calendar.DATE, 1);  // number of days to add
			String dateUntil = dateFormat.format(c.getTime()).replaceAll("/", "-");  // dt is now the new date
			String city = translate(location.getCity());

			//author
			String[] arra = {"querysearch=" + event.getArtist(), "since=" + dateSince, "until=" + dateUntil};
			System.out.println("inizio a cercare last24hauth...");
			int last24a = Exporter.search(arra);
			m.setTwitter_last24hAuthorOnly(last24a);
			System.out.println(last24a);
			measurementDAOImpl.updateMeasurementTwitterLast24Authors(m);


			// author + city
			String[] arr = {"querysearch=" + event.getArtist() + " " + city, "since=" + dateSince, "until=" + dateUntil};
			System.out.println("inizio a cercare last24h...");
			int last24 = Exporter.search(arr);
			m.setTwitter_last24h(last24);
			System.out.println(last24);
			measurementDAOImpl.updateMeasurementTwitterLast24(m);


			System.out.println(m.toString());
			i++;
		}
	}




	public static void last24h(List<Measurement> measurements) throws SQLException, ParseException, IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		String date = dateFormat.format(new Date());
		Date today = dateFormat.parse(date);
		for (Measurement m : measurements){
			Date measurementDate = dateFormat.parse(m.getDate()); 
			//solo se la data è minore di oggi
			if (measurementDate.before(today)){
				Event event = eventDAOImpl.getEvent(m.getEvent());
				Location location = locationDAOImpl.getLocation(event.getLocation());
				String dateSince = m.getDate().replaceAll("/", "-");
				Date since = dateFormat.parse(m.getDate());
				Calendar c = Calendar.getInstance();
				c.setTime(since);
				c.add(Calendar.DATE, 1);  // number of days to add
				String dateUntil = dateFormat.format(c.getTime()).replaceAll("/", "-");  // dt is now the new date
				String city = translate(location.getCity());
				String[] arr = {"querysearch=" + event.getArtist() + " " + city, "since=" + dateSince, "until=" + dateUntil};
				int last24 = Exporter.search(arr);
				m.setTwitter_last24h(last24);
				measurementDAOImpl.updateMeasurementTwitterLast24(m);
			}
		}

	}

	public static void last24hAuthorsOnly(List<Measurement> measurements) throws SQLException, ParseException, IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = dateFormat.format(new Date());
		Date today = dateFormat.parse(date);
		for (Measurement m : measurements){
			Date measurementDate = dateFormat.parse(m.getDate()); 
			//solo se la data è minore di oggi
			if (measurementDate.before(today)){
				Event event = eventDAOImpl.getEvent(m.getEvent());
				String dateSince = m.getDate().replaceAll("/", "-");
				Date since = dateFormat.parse(m.getDate());
				Calendar c = Calendar.getInstance();
				c.setTime(since);
				c.add(Calendar.DATE, 1);  // number of days to add
				String dateUntil = dateFormat.format(c.getTime()).replaceAll("/", "-");  // dt is now the new date
				String[] arr = {"querysearch=" + event.getArtist(), "since=" + dateSince, "until=" + dateUntil};
				int last24 = Exporter.search(arr);
				m.setTwitter_last24hAuthorOnly(last24);
				measurementDAOImpl.updateMeasurementTwitterLast24Authors(m);
			}
		}

	}

	public static void last24hCombined(List<Measurement> measurements, String dateToCheck) throws SQLException, ParseException, IOException{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		//String date = dateFormat.format(new Date());
		//Date today = dateFormat.parse(date);
		List<Measurement> measurementsDateToCheck = new ArrayList<Measurement>();
		//filtro solo date di interesse
		for (Measurement measurement : measurements) {
			if (measurement.getDate().equals(dateToCheck))
				measurementsDateToCheck.add(measurement);
		}
		//int total = measurementsDate.size();
		int i = 0;
		for (Measurement m : measurementsDateToCheck){
//			if (measurementsDateToCheck.indexOf(m) > 163){
			
			//Date measurementDate = dateFormat.parse(m.getDate()); 
			//solo se la data è minore di oggi
			//if (measurementDate.before(today)){
			System.out.println("misurazione numero: " + i + " su " + measurementsDateToCheck.size() + 
					" misurazioni, data: " + dateToCheck);
			Event event = eventDAOImpl.getEvent(m.getEvent());
			Location location = locationDAOImpl.getLocation(event.getLocation());
			String dateSince = m.getDate().replaceAll("/", "-");
			Date since = dateFormat.parse(m.getDate());
			Calendar c = Calendar.getInstance();
			c.setTime(since);
			c.add(Calendar.DATE, 1);  // number of days to add
			String dateUntil = dateFormat.format(c.getTime()).replaceAll("/", "-");  // dt is now the new date
			String city = translate(location.getCity());

			// author + city
			String[] arr = {"querysearch=" + event.getArtist() + " " + city, "since=" + dateSince, "until=" + dateUntil};
			int last24 = Exporter.search(arr);
			m.setTwitter_last24h(last24);
			measurementDAOImpl.updateMeasurementTwitterLast24(m);

			//author
			String[] arra = {"querysearch=" + event.getArtist(), "since=" + dateSince, "until=" + dateUntil};
			int last24a = Exporter.search(arra);
			m.setTwitter_last24hAuthorOnly(last24a);
			measurementDAOImpl.updateMeasurementTwitterLast24Authors(m);

			System.out.println(m.toString());

//			}
			i++;
		}
		System.out.println("rilevata data: " + dateToCheck);

	}
	private static String translate(String city) {
		switch (city){
		case "Rome": city = "Roma"; break;
		case "Milan": city = "Milano"; break;
		case "Turin": city = "Torino"; break;
		case "Naples": city = "Napoli";	break;
		case "Florence": city = "Firenze"; break;
		case "Bari": city = "Bari"; break;
		case "Verona": city = "Verona"; break;
		}
		return city;
	}
}

