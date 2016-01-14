package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.base.GeneratorBase;

import dao.EventDAOImpl;
import dao.LocationDAOImpl;
import model.Event;
import model.Location;

/*
 * 1-look for metro-area-id for a query
 * 2-get all events in the metro-area-id and creates a list "eventList" 
 * 
 * */
public class SongKickSearch {

	private final static String apiKey = "rBfCuvN98YPUeD4r";

	private final static List<Event> eventList = new ArrayList<Event>();
	private final static EventDAOImpl daoEvents = new EventDAOImpl();
	private final static LocationDAOImpl daoLocations = new LocationDAOImpl(); 





	public static void main(String[] args) throws IOException, JSONException, SQLException {
		String[] cities = {"roma,Italy", "milano,Italy", "torino,Italy", "napoli,Italy", "firenze,Italy", "verona,Italy", "bari,Italy"/*,  "london,UK","paris,France"*/};
		for (int j = 0; j < cities.length; j++ ){
			String city = getLocationIndex(cities[j]);
			System.out.println(city);
			int totalEntries = getTotalEntries(city);
			int pages = totalEntries/50 + 1;
			for (int i = 1; i <= pages; i++){
				System.out.println("Città: " + cities[j] + ". Pagina: " + i);
				searchEvents(city, i);
			}
		}
	}

	private static String getLocationIndex(String city_state) throws JSONException, IOException {
		//get location id
		String[] tokenizer = city_state.split(",");
		String queryLocation = ("http://api.songkick.com/api/3.0/"
				+ "search/locations.json"
				+ "?query=" + tokenizer[0] + "&apikey="+ apiKey);
		System.out.println(queryLocation);
		JSONArray locations = readJsonFromUrl(queryLocation)
				.getJSONObject("resultsPage")
				.getJSONObject("results")
				.getJSONArray("location");

		int index = getLocationIndex(locations, tokenizer[1]);
		Object id = locations.getJSONObject(index).getJSONObject("metroArea").get("id");
		String cityId = id.toString();
		return cityId;
	}

	private static int getTotalEntries(String cityId) throws JSONException, IOException {
		String queryEvents = ("http://api.songkick.com/api/3.0/events.json?apikey="+ apiKey +
				"&location=sk:" + cityId +
				"&page=3");
		JSONObject events = readJsonFromUrl(queryEvents);
		String tot = events.getJSONObject("resultsPage").get("totalEntries").toString();
		return Integer.parseInt(tot);

	}

	private static void searchEvents(String cityId, int page) throws JSONException, IOException, SQLException {
		//query events located in "id"
		//modificare query per più pagine e più risultati
		String queryEvents = ("http://api.songkick.com/api/3.0/events.json?apikey="+ apiKey +
				"&location=sk:" + cityId +
				"&page=" + page);
		JSONObject events = readJsonFromUrl(queryEvents);
		//System.out.println(events);
		JSONArray eventArray = events.getJSONObject("resultsPage").getJSONObject("results").getJSONArray("event");
		//System.out.println(eventArray);

		//popola una lista di oggetti evento per la query data


		for (int i = 0; i < eventArray.length(); i++){
			JSONObject obj = eventArray.getJSONObject(i);
			String name = obj.get("displayName").toString();
			String date = obj.getJSONObject("start").get("date").toString();
			//List<String> artistsList = new ArrayList<String>();
			JSONArray artistsJSON = obj.getJSONArray("performance");
			if (!artistsJSON.toString().equals("[]")){
				String artists = artistsJSON.getJSONObject(0).getJSONObject("artist").get("displayName").toString();
				for (int j = 1; j < artistsJSON.length(); j++){
					JSONObject art = artistsJSON.getJSONObject(j);
					String artist = art.getJSONObject("artist").get("displayName").toString();
					artists.concat(", " + artist);
				}

				String popularity = obj.get("popularity").toString();


				//formatta popolarità
				double popularityFormatted = popularityNormalizer(popularity);

				if (popularityFormatted > 0.004){


					String type = obj.get("type").toString();
					String nation = obj.getJSONObject("venue").getJSONObject("metroArea")
							.getJSONObject("country").get("displayName").toString(); 
					String city = obj.getJSONObject("venue").getJSONObject("metroArea")
							.get("displayName").toString(); 
					String place = obj.getJSONObject("venue").get("displayName").toString();
					Location location = new Location(nation, city, place);
					daoLocations.insertLocation(location);
					int location_id = daoLocations.getId(nation,city,place);

					Event event = new Event(name, date, location_id, artists, popularityFormatted, type);
					if (!eventExist(event)){
						daoEvents.insertEvent(event);
						//eventList.add(event);
					}
				}
			}

		}

	}

	private static boolean eventExist(Event event) throws SQLException {
		if (daoEvents.getEvent(event.getName(), event.getDate(), event.getArtist()) != null){
			return true;
		}
		else return false;
	}

	private static int getLocationIndex(JSONArray json, String nation){
		//check for correct city
		int i;
		for (i = 0; i < json.length(); i++){
			JSONObject location = json.getJSONObject(i);
			if ((location.getJSONObject("city").getJSONObject("country").get("displayName")).equals(nation)){
				break;
			}
		}
		return i;
	}


	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	public static Double popularityNormalizer(String popularity){
		popularity = popularity.replace(',', '.');
		Double number = Double.parseDouble(popularity);
		NumberFormat formatter = new DecimalFormat("#");
		formatter.setMaximumFractionDigits(10);
		String form = formatter.format(number);
		form =  form.replace(',', '.');
		return Double.parseDouble(form);
	}




}
