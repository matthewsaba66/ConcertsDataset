package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.w3c.dom.html.HTMLIsIndexElement;

import com.jcabi.aspects.Timeable;
import com.mysql.jdbc.jmx.LoadBalanceConnectionGroupManager;

import dao.EventDAOImpl;
import dao.LocationDAOImpl;
import dao.MeasurementDAOImpl;
import facebook4j.Picture;
import main.Exporter;
import model.Event;
import model.Location;
import model.Measurement;

public class Testing {
	private static MeasurementDAOImpl measurementDAOImpl = new MeasurementDAOImpl();



	static String translate(String city) {
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

	public static void main(String[] args) throws ParseException, InterruptedException, ExecutionException, SQLException, IOException {
		//double number = 0.45864846516;
		/*	number1 = number1.replace(',', '.');
		Double number = Double.parseDouble(number1);
		NumberFormat formatter = new DecimalFormat("#0.000000");
		String form = formatter.format(number);
		form =  form.replace(',', '.');
		System.out.println(Double.parseDouble(form));*/
		//popularity = popularity.replace(',', '.');
		//Double number = Double.parseDouble(popularity);

		/*	NumberFormat formatter = new DecimalFormat("#.000");
		formatter.setMaximumFractionDigits(3);
		String form = formatter.format(number);
		form =  form.replace(',', '.');
		System.out.println(Double.parseDouble(form));

		 */

		/*	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date1 = dateFormat.parse("2015/12/8");
		Date date2 = new Date();
		System.out.println(date1.toString());
		System.out.println(date2.toString());

		System.out.println(date1.compareTo(date2));
		System.out.println(date2.compareTo(date1));		
		System.out.println(date1.compareTo(date1));


		String date = dateFormat.format(new Date());
		Date today = dateFormat.parse(date);
		System.out.print(date1.compareTo(today));

		 */	
		/*	int y1 = 34;
		int y2= 78;
		int x1 = 0;
		int x2 = 8;

		int a = y1 - y2;
		int b = x2 - x1;
		int c = (x1-x2)*y1;

		double f1, f2, f3, f4, f5, f6, f7;
		f1 = (a * 1 + c)/(-b);
		f2 = (a * 2 + c)/(-b);
		f3 = (a * 3 + c)/(-b);
		f4 = (a * 4 + c)/(-b);
		f5 = (a * 5 + c)/(-b);
		f6 = (a * 6 + c)/(-b);
		f7 = (a * 7 + c)/(-b);
		System.out.println(f1);
		System.out.println(f2);
		System.out.println(f3);
		System.out.println(f4);
		System.out.println(f5);
		System.out.println(f6);
		System.out.println(f7);
		int splits = 7;
		int[] test = interpolate(34, 78, 7);
		for (int i : test)
			System.out.println(i);

		 */
		/*	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		Date date = dateFormat.parse("2015/20/10");
		System.out.println(isWithinRange(date));*/
		/*
	private static boolean isWithinRange(Date testDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date startDate = dateFormat.parse("2015/12/08");
		Date endDate = dateFormat.parse("2015/12/16");

		return !(testDate.before(startDate) || testDate.after(endDate));
	}


	public static int[] interpolate(int y1, int y2, int splits){
		int[] results = new int[splits];

		int x2 = splits + 1;

		int a = y1 - y2;
		int b = x2 - 0;
		int c = (-x2)*y1 + (y2-y1)*0;

		for (int i = 0; i < splits; i++){
			int x = (a * (i + 1) + c)/(-b);
			results[i] = x;
		}
		return results;
	}*/


		/*
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		EventDAOImpl eventDAOImpl = new EventDAOImpl();
		LocationDAOImpl locationDAOImpl = new LocationDAOImpl();
		String date = dateFormat.format(new Date());
		Date today = dateFormat.parse(date);

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

			System.out.println("uscito da load");
			m.setTwitter_last24hAuthorOnly(last24a);
			System.out.println(last24a);
			//measurementDAOImpl.updateMeasurementTwitterLast24Authors(m);
		}
		 */
		/*String test = "250.000.000.000.000.000.000.000.000";
		test = test.replace(".", "");
		System.out.println(test);*/
		//System.out.println(Integer.parseInt(test));
		
		//metodo 1 = torna la lista con le righe del file
		BufferedReader in = new BufferedReader(new FileReader("user-agents/user-agents.txt"));
		String str;
		List<String> list = new ArrayList<String>();
	    while((str = in.readLine()) != null){
	        list.add(str);
	    }
	    in.close();
	    
	    
	    //metodo 2 = torna la stringa presa a random
	    int random = new Random().nextInt(list.size());
	    String userAgent = list.get(random);
	    System.out.println(userAgent);
	    
	    

	}

}




