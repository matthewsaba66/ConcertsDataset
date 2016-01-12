package main;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import dao.EventDAOImpl;
import dao.MeasurementDAOImpl;
import model.Event;
import model.Measurement;

public class Interpolator {

	public static void interpolate(Event event) throws SQLException, ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

		
		MeasurementDAOImpl measurementDAOImpl = new MeasurementDAOImpl();
		
		
		//Event event = eventDAOImpl.getEvent(eventId);
		List<Measurement> measurements = measurementDAOImpl.getMeasurementsForEvent(event.getId());
		List<Measurement> measSmall = new ArrayList<Measurement>();
		Date eventDate = dateFormat1.parse(event.getDate());
		if (eventDate.after(dateFormat.parse("2015/12/15"))){
			for (Measurement measurement : measurements){
				if (isWithinRange(dateFormat.parse(measurement.getDate())))
					measSmall.add(measurement);
			}
			/*for (Measurement m : measSmall){
				System.out.println(m.toString());
			}*/
			int y1 = measSmall.get(0).getFacebook_measurement();
			int y2 = measSmall.get(measSmall.size() - 1).getFacebook_measurement();
			//System.out.println(y1);
			//System.out.println(y2);
			int[] interpolated = interpolate(y1, y2, measSmall.size() - 2);

			/*for (int i : interpolated)
				System.out.println(i);*/
			measSmall.remove(0);
			measSmall.remove(measSmall.size() - 1);
			int i = 0;
			for (Measurement measurement : measSmall){
				measurement.setFacebook_measurement(interpolated[i]);
				measurementDAOImpl.updateMeasurementFacebook(measurement);
				i++;		
			}
			System.out.println("--------------" + event.toString() + "------------------Terminato");
		}
		else {
			System.out.println("mega-errore");
		}



	}

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
	}
	
	public static void main(String[] args) throws SQLException, ParseException{
		EventDAOImpl eventDAOImpl = new EventDAOImpl();
		List<Event> events = eventDAOImpl.getEvents();
		for (Event event : events){
			interpolate(event);
		}
	}

}
