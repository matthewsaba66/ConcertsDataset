package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import model.Event;
import model.Location;

public class EventDAOImpl implements EventDAO {

	private Connection connect = this.getConnection();
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;


	public Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connection == null)
				connection = DriverManager.getConnection("jdbc:mysql://localhost/concertsdataset?user=root&password=password");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return connection;
	}

	@Override
	public List<Event> getEvents() throws SQLException {
		List<Event> events = new ArrayList<Event>();
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		resultSet = statement
				.executeQuery("select * from concertsdataset.events");
		while(resultSet.next()){
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			String date = resultSet.getString(3);
			int location = resultSet.getInt(4);
			String type = resultSet.getString(5);
			double popularity = resultSet.getDouble(6);
			String artists = resultSet.getString(7);

			Event event = new Event(id, name, date, location, artists, popularity, type);
			events.add(event);
		}
		statement.close();
		return events;
	}

	@Override
	public Event getEvent(int id) throws SQLException {
		Event event;
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connect.prepareStatement("select * from concertsdataset.events where event_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();

		resultSet.next();
		int idE = resultSet.getInt(1);
		String name = resultSet.getString(2);
		String date = resultSet.getString(3);
		int location = resultSet.getInt(4);
		String type = resultSet.getString(5);
		double popularity = resultSet.getDouble(6);
		String artists = resultSet.getString(7);

		event = new Event(idE, name, date, location, artists, popularity, type);
		statement.close();
		return event;
	}

	@Override
	public void insertEvent(Event event) throws SQLException {

		if (getEvent(event.getName(), event.getDate(), event.getArtist())!=null){
			return;
		}

		// PreparedStatements can use variables and are more efficient
		preparedStatement = connect
				.prepareStatement("insert into  concertsdataset.events (name, date, location, type, popularity, artists) values ( ?, ?, ?, ?, ?, ? )");

		// Parameters start with 1
		preparedStatement.setString(1, event.getName());
		preparedStatement.setString(2, event.getDate());
		preparedStatement.setInt(3, event.getLocation());
		preparedStatement.setString(4, event.getType());
		preparedStatement.setDouble(5, event.getPopularity());
		preparedStatement.setString(6, event.getArtist());

		preparedStatement.executeUpdate();
		
		//connection.close();
		statement.close();

	}

	@Override
	public void deleteEvent(Event event) throws SQLException {
		// Remove again the insert comment
		preparedStatement = connect
				.prepareStatement("delete from concertsdataset.events where event_id= ? ; ");
		preparedStatement.setInt(1, event.getId());
		preparedStatement.executeUpdate();

	}

	@Override
	public Event getEvent(String name, String date,String artist) throws SQLException {
		Event event;
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connect.prepareStatement("select * from concertsdataset.events where name=? and date=? and artists=?");
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, date);
		preparedStatement.setString(3, artist);
		resultSet = preparedStatement.executeQuery();
		boolean set = resultSet.next();
		if (!set){
			return null;
		}
		
		int idE = resultSet.getInt(1);
		name = resultSet.getString(2);
		date = resultSet.getString(3);
		int location = resultSet.getInt(4);
		String artists = resultSet.getString(5);
		double popularity = resultSet.getDouble(6);
		String type = resultSet.getString(7);

		event = new Event(idE, name, date, location, artists, popularity, type);
		statement.close();
		return event;
	}
	
	

	@Override
	public boolean eventExists(Event event) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}


	public static void main(String[] args) throws SQLException {
		EventDAOImpl dao= new EventDAOImpl();
		Event event = new Event("muses drones tour", "15-05-2016", 45, "muse", 0.75, "concert");
		//System.out.println(dao.getEvent("muse drones tour", "15-05-2016", "muse").toString());
		dao.insertEvent(event);
	}

}
