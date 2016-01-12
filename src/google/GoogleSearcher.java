package google;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Event;
import model.Location;


public class GoogleSearcher {
	private List<String> userAgents;

	
	
	public GoogleSearcher() throws IOException {
		super();
		this.userAgents = mkUAlist();
	}



	public long getFoundPages(Event event, Location location) throws IOException {
		String query = event.getArtist() + " " + translate(location.getCity()) + " " + location.getPlace();
		String request = "https://www.google.com/search?q=" + query /*+ "&num=20"*/;
		//System.out.println("Sending request..." + request);
		String sub = null;
		String userAgent = "";
		//int count = 0;
		//int maxTries = 10;
		while (true){
			try {
				userAgent = getUserAgent(userAgents);
				// need http protocol, set this as a Google bot agent :)
				Document doc = Jsoup
						.connect(request)
						.userAgent(userAgent) 
						.timeout(5000).get();

				// get all links
				Elements links = doc.select("#resultStats");
				Element link = links.get(0);
				String stats = link.text();

				String pages = stats.replace(".", "");
				return Long.parseLong(extractNumber(pages));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (IndexOutOfBoundsException e){
				//se finisco qui vuol dire che l'user agent non è valido, quindi lo rimuovo dalla lista e rieto
				userAgents.remove(userAgents.indexOf(userAgent));
				//if (++count == maxTries) throw e;
			}
		}
		//return "null";
	}
	
	
	
	private List<String> mkUAlist() throws IOException {
		//metodo 1 = torna la lista con le righe del file
		BufferedReader in = new BufferedReader(new FileReader("user-agents/user-agents.txt"));
		String str;
		List<String> list = new ArrayList<String>();
		while((str = in.readLine()) != null){
			list.add(str);
		}
		in.close();
		return list;
	}

	private String getUserAgent(List<String> userAgents) throws FileNotFoundException {
		//metodo 2 = torna la stringa presa a random
		int random = new Random().nextInt(userAgents.size());
		String userAgent = userAgents.get(random);
		return userAgent;
	}

	public static String extractNumber(final String str) {
		if(str == null || str.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c : str.toCharArray()){
			if(Character.isDigit(c)){
				sb.append(c);
				found = true;
			} else if(found){
				// If we already found a digit before and this char is not a digit, stop looping
				break;                
			}
		}
		return sb.toString();
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
