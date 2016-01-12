package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Location;

public class LocationDAOImpl implements LocationDAO {

	private Connection connection = this.getConnection();
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;


	public Connection getConnection(){
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
	public List<Location> getLocations() throws SQLException {
		List<Location> locations = new ArrayList<Location>();
		// Statements allow to issue SQL queries to the database
		statement = connection.createStatement(); 
		// Result set get the result of the SQL query
		resultSet = statement
				.executeQuery("select * from concertsdataset.locations");
		while(resultSet.next()){
			int id = resultSet.getInt(1);
			String state = resultSet.getString(2);
			String city = resultSet.getString(3);
			String place = resultSet.getString(4);


			Location location = new Location(id, state, city, place);
			locations.add(location);
		}
		statement.close();
		return locations;
	}

	@Override
	public Location getLocation(int id) throws SQLException {
		Location location;
		// Statements allow to issue SQL queries to the database
		statement = connection.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connection.prepareStatement("select * from concertsdataset.locations where location_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();

		resultSet.next();
		int idL = resultSet.getInt(1);
		String state = resultSet.getString(2);
		String city = resultSet.getString(3);
		String place = resultSet.getString(4);

		location = new Location(idL, state, city, place);

		statement.close();
		return location;
	}

	@Override
	public void insertLocation(Location location) throws SQLException {

		if (getLocation(location.getState(), location.getCity(), location.getPlace())!=null){
			statement.close();
			return;
		}
		
			// PreparedStatements can use variables and are more efficient
			preparedStatement = connection
					.prepareStatement("insert into  concertsdataset.locations (state, city, place) values (  ?, ?, ? )");

			// Parameters start with 1

			preparedStatement.setString(1, location.getState());
			preparedStatement.setString(2, location.getCity());
			preparedStatement.setString(3, location.getPlace());

			preparedStatement.executeUpdate();
			statement.close();

	}

	@Override
	public void deleteLocation(Location location) throws SQLException {
		// Remove again the insert comment
		preparedStatement = connection
				.prepareStatement("delete from concertsdataset.locations where location_id= ? ; ");
		preparedStatement.setInt(1, location.getId());
		preparedStatement.executeUpdate();
		statement.close();

	}

	@Override
	public Location getLocation(String state, String city, String place) throws SQLException {
		Location location;
		// Statements allow to issue SQL queries to the database
		statement = connection.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connection.prepareStatement("select * from concertsdataset.locations where state=? and city=? and place=? ;");
		preparedStatement.setString(1, state);
		preparedStatement.setString(2, city);
		preparedStatement.setString(3, place);
		resultSet = preparedStatement.executeQuery();
		boolean set = resultSet.next();

		if (!set){
			statement.close();
			return null;
		}

		int idL = resultSet.getInt(1);
		state = resultSet.getString(2);
		city = resultSet.getString(3);
		place = resultSet.getString(4);

		location = new Location(idL, state, city, place);
		statement.close();
		return location;
	}
	
	@Override
	public int getId(String nation, String city, String place) throws SQLException {
		// Statements allow to issue SQL queries to the database
				statement = connection.createStatement(); 
				// Result set get the result of the SQL query
				preparedStatement = connection.prepareStatement("select location_id from concertsdataset.locations where state=? and city=? and place=? ;");
				preparedStatement.setString(1, nation);
				preparedStatement.setString(2, city);
				preparedStatement.setString(3, place);
				resultSet = preparedStatement.executeQuery();
				boolean set = resultSet.next();

				int idL = resultSet.getInt(1);
				statement.close();
				return idL;
	}
	
	

	public static void main(String[] args) throws SQLException {
		LocationDAOImpl dao= new LocationDAOImpl();
		Location location = new Location( "italy", "roma", "auditorium");
		dao.insertLocation(location);
		System.out.println(dao.getLocation(3).toString());
		//System.out.println(dao.getId( "italy", "roma", "auditorium"));
	}

	
}


