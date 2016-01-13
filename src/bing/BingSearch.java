package bing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;

import javax.xml.ws.WebEndpoint;

import org.apache.commons.codec.binary.Base64;

import model.Event;
import model.Location;


public class BingSearch {

	public static void main(String[] args) {
		//int[] articles = new int[2];
		String searchText = "muse milan mediolanum forum";
		searchText = searchText.replaceAll(" ", "%20");
		String accountKey="kX5iTCEfcsXt+CfJ1utIjp3108TujtRbXpMZrfZPYL4";  
		//chiave 1 con account hotmail = "kX5iTCEfcsXt+CfJ1utIjp3108TujtRbXpMZrfZPYL4"
		//chiave 2 con account universitario da ottenere = ""
		

		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);
		URL url;
		try {
			url = new URL(
					"https://api.datamarket.azure.com/Bing/Search/v1/Composite?Sources=%27web%2Bnews%27&Query=%27" + searchText + "%27&$format=JSON");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			//System.out.println("Output from Server .... \n");
			//write json to string sb
			while ((output = br.readLine()) != null) {

				sb.append(output);

			}

			conn.disconnect();
			System.out.println(sb);
			//find webtotal
			int find0 = sb.indexOf("\"WebTotal\":\"");
			int startindex0 = find0 + 12;
			int lastindex0 = sb.indexOf("\",\"WebOffset\"");

			//find newstotal among output      
			int find1= sb.indexOf("\"NewsTotal\":\"");
			int startindex1 = find1 + 13;
			int lastindex1 = sb.indexOf("\",\"NewsOffset\"");

			System.out.println("News Total: " + sb.substring(startindex1, lastindex1));
			System.out.println("Web Total: " + sb.substring(startindex0, lastindex0));

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}


	}

	public static void getNews(Event event, Location venue) throws SQLException {

		int newsTotal = -1;

		String searchText = event.getArtist() + " " + venue.getCity() + " " + venue.getPlace();

		String formattedSearchText = searchText.replaceAll(" ", "%20");
		String accountKey="kX5iTCEfcsXt+CfJ1utIjp3108TujtRbXpMZrfZPYL4";

		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);
		URL url;
		try {
			url = new URL(
					"https://api.datamarket.azure.com/Bing/Search/v1/Composite?"
							+ "Sources=%27news%27&Query=%27" + formattedSearchText + "%27&$format=JSON");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			//System.out.println("Output from Server .... \n");
			//write json to string sb
			while ((output = br.readLine()) != null) {

				sb.append(output);

			}

			conn.disconnect();
			//System.out.println(sb);
			//find webtotal among output      
			int find= sb.indexOf("\"NewsTotal\":\"");
			int startindex = find + 13;


			int lastindex = sb.indexOf("\",\"NewsOffset\"");
			//return Integer.parseInt(sb.substring(startindex, lastindex));

			System.out.println(searchText);
			try{
				newsTotal = Integer.parseInt(sb.substring(startindex, lastindex));
			}
			catch(NumberFormatException e){
				System.out.println("News for string '" + searchText + "': not achieved! \n");
				return;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		System.out.println("News for string '" + searchText + "': " + newsTotal + "\n");
	}



	public static int[] getNewsInt(Event event, Location venue) throws SQLException {
		int[] bing = new int[2];
		int newsTotal = -1;
		int webTotal = -1;

		String searchText = event.getArtist() + " " + venue.getCity() + " " + venue.getPlace();

		String formattedSearchText = searchText.replaceAll(" ", "%20");
		String accountKey="kX5iTCEfcsXt+CfJ1utIjp3108TujtRbXpMZrfZPYL4";

		byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
		String accountKeyEnc = new String(accountKeyBytes);
		URL url;
		try {
			url = new URL(
					"https://api.datamarket.azure.com/Bing/Search/v1/Composite?"
							+ "Sources=%27web%2Bnews%27&Query=%27" + formattedSearchText + "%27&$format=JSON");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String output;
			//System.out.println("Output from Server .... \n");
			//write json to string sb
			while ((output = br.readLine()) != null) {

				sb.append(output);

			}

			conn.disconnect();
			//System.out.println(sb);
			//find webtotal among output      
			//find webtotal
			int find0 = sb.indexOf("\"WebTotal\":\"");
			int startindex0 = find0 + 12;
			int lastindex0 = sb.indexOf("\",\"WebOffset\"");

			//find newstotal among output      
			int find1= sb.indexOf("\"NewsTotal\":\"");
			int startindex1 = find1 + 13;
			int lastindex1 = sb.indexOf("\",\"NewsOffset\"");


			//System.out.println(searchText);
			try{
				webTotal = Integer.parseInt(sb.substring(startindex0, lastindex0));
				bing[0] = webTotal;
			}
			catch(NumberFormatException e){

				bing[0] = 0;
			}
			try{
				newsTotal = Integer.parseInt(sb.substring(startindex1, lastindex1));
				bing[1] = newsTotal;
			}
			catch(NumberFormatException e){

				bing[1] = 0;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return bing;
	}
}

