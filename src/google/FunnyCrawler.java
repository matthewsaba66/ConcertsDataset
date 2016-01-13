package google;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FunnyCrawler {

	//private static Pattern patternDomainName;
	//private Matcher matcher;
	//private static final String DOMAIN_NAME_PATTERN 
	//= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
	/*static {
		patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
	}*/

	public static void main(String[] args) throws IOException {

		FunnyCrawler obj = new FunnyCrawler();
		String result = obj.getDataFromGoogle("Chantal Acda with The Leisure Society Chiesa Evangelica Metodista");
		System.out.println(extractNumber(result));

	}

	private String getDataFromGoogle(String query) throws IOException {

		String request = "https://www.google.com/search?q=" + query /*+ "&num=20"*/+ "&tbm=nws";
		//System.out.println("Sending request..." + request);
		String sub = null;
		List<String> userAgents = mkUAlist();
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

				sub = stats.replace(".", "");
				return sub;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (IndexOutOfBoundsException e){
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

}
