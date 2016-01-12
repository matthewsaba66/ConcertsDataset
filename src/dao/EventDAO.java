package dao;

import java.sql.SQLException;
import java.util.List;

import model.Event;

public interface EventDAO {
	public List<Event> getEvents() throws SQLException;
	public Event getEvent(int id) throws SQLException;
	public void insertEvent(Event event) throws SQLException;
	public void deleteEvent(Event event) throws SQLException;
	public Event getEvent(String name, String date, String artists) throws SQLException;
	public boolean eventExists(Event event) throws SQLException;

}

