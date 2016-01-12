package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import model.Measurement;

public class MeasurementDAOImpl implements MeasurementDAO {

	private Connection connect = this.getConnection();
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	Connection connection = null;

	public Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connection == null)
				connection = DriverManager.getConnection("jdbc:mysql://localhost/concertsdataset?"
						+ "user=root&password=password");

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return connection;
	}

	@Override
	public List<Measurement> getMeasurements() throws SQLException {
		List<Measurement> measurements = new ArrayList<Measurement>();
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		resultSet = statement
				.executeQuery("select * from concertsdataset.measurements");
		while(resultSet.next()){
			int id = resultSet.getInt(1);
			String date = resultSet.getString(2);
			int event = resultSet.getInt(3);
			int bingWeb = resultSet.getInt(4);
			int bingNews = resultSet.getInt(5);
			int facebookMeasurement = resultSet.getInt(6);
			double twitter_last10 = resultSet.getDouble(7);
			int twitter_last24h = resultSet.getInt(8);
			int twitter_last24hAuthorOnly = resultSet.getInt(9);
			long google_foundPages = resultSet.getLong(10);



			Measurement measurement = new Measurement(id, date, event, bingWeb, bingNews,
					facebookMeasurement, twitter_last10, twitter_last24h, twitter_last24hAuthorOnly,
					google_foundPages);
			measurements.add(measurement);
		}
		statement.close();
		return measurements;
	}

	@Override
	public Measurement getMeasurement(int id) throws SQLException {
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connect.prepareStatement("select * from concertsdataset.measurements "
				+ "where measurement_id=?");
		preparedStatement.setInt(1, id);
		resultSet = preparedStatement.executeQuery();

		resultSet.next();
		int idM = resultSet.getInt(1);
		String date = resultSet.getString(2);
		int event = resultSet.getInt(3);
		int bingWeb = resultSet.getInt(4);
		int bingNews = resultSet.getInt(5);
		int facebookMeasurement = resultSet.getInt(6);
		double twitter_last10 = resultSet.getDouble(7);
		int twitter_last24h = resultSet.getInt(8);
		int twitter_last24hAuthorOnly = resultSet.getInt(9);
		long google_foundPages = resultSet.getLong(10);


		Measurement measurement = new Measurement(idM, date, event, bingWeb, bingNews,
				facebookMeasurement, twitter_last10, twitter_last24h, twitter_last24hAuthorOnly, 
				google_foundPages);
		statement.close();
		return measurement;
	}

	@Override
	public void insertMeasurement(Measurement measurement) throws SQLException {
		if (getMeasurement(measurement.getDate(), measurement.getEvent())!=null){
			return;
		}

		// PreparedStatements can use variables and are more efficient
		preparedStatement = connect
				.prepareStatement("insert into  concertsdataset.measurements "
						+ "(date, event, bing_web, bing_news, facebook_measurement, "
						+ "twitter_last10, twitter_last24h, twitter_last24hAuthorOnly, "
						+ "google_foundPages)"
						+ " values (  ?, ?, ?, ?, ?, ?, ?, ?, ? )");

		// Parameters start with 1

		preparedStatement.setString(1, measurement.getDate());
		preparedStatement.setInt(2, measurement.getEvent());
		preparedStatement.setInt(3, measurement.getBing_web());
		preparedStatement.setInt(4, measurement.getBing_news());
		preparedStatement.setInt(5, measurement.getFacebook_measurement());
		preparedStatement.setDouble(6, measurement.getTwitter_last10());
		preparedStatement.setInt(7, measurement.getTwitter_last24h());
		preparedStatement.setInt(8, measurement.getTwitter_last24hAuthorOnly());
		preparedStatement.setLong(9, measurement.getGoogle_foundPages());

		preparedStatement.executeUpdate();
		statement.close();


	}

	@Override
	public void deleteMeasurement(Measurement measurement) throws SQLException {
		// Remove again the insert comment
		preparedStatement = connect
				.prepareStatement("delete from concertsdataset.measurements"
						+ " where measurement_id= ? ; ");
		preparedStatement.setInt(1, measurement.getId());
		preparedStatement.executeUpdate();
		statement.close();


	}

	@Override
	public Measurement getMeasurement(String date, int event) throws SQLException {
		Measurement measurement;
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connect.prepareStatement("select * from concertsdataset.measurements"
				+ " where date=? and event=? ;");
		preparedStatement.setString(1, date);
		preparedStatement.setInt(2, event);
		resultSet = preparedStatement.executeQuery();
		boolean set = resultSet.next();

		if (!set){
			return null;
		}

		int idM = resultSet.getInt(1);
		date = resultSet.getString(2);
		event = resultSet.getInt(3);
		int bing_web = resultSet.getInt(4);
		int big_news = resultSet.getInt(5);
		int facebook_measurement = resultSet.getInt(6);
		double twitter_last10 = resultSet.getDouble(7);
		int twitter_last24h = resultSet.getInt(8);
		int twitter_last24hAuthorOnly = resultSet.getInt(9);
		long google_foundPages = resultSet.getLong(10);


		measurement = new Measurement
				(idM, date, event, bing_web, big_news, facebook_measurement, twitter_last10, 
						twitter_last24h, twitter_last24hAuthorOnly, google_foundPages);
		statement.close();
		return measurement;
	}


	@Override
	public void updateMeasurementTwitterLast24(Measurement measurement) throws SQLException {

		// PreparedStatements can use variables and are more efficient
		preparedStatement = connect
				.prepareStatement("update concertsdataset.measurements set twitter_last24h = ? "
						+ "where measurement_id = ?");

		// Parameters start with 1

		preparedStatement.setInt(1, measurement.getTwitter_last24h());
		preparedStatement.setInt(2, measurement.getId());

		preparedStatement.executeUpdate();
		statement.close();	
	}


	@Override
	public List<Measurement> getMeasurementsForEvent(int eventId) throws SQLException {
		List<Measurement> measurements = new ArrayList<Measurement>();
		// Statements allow to issue SQL queries to the database
		statement = connect.createStatement(); 
		// Result set get the result of the SQL query
		preparedStatement = connect.prepareStatement("select * from concertsdataset.measurements "
				+ "where event=?");
		preparedStatement.setInt(1, eventId);
		resultSet = preparedStatement.executeQuery();

		while(resultSet.next()){
			int id = resultSet.getInt(1);
			String date = resultSet.getString(2);
			int event = resultSet.getInt(3);
			int bingWeb = resultSet.getInt(4);
			int bingNews = resultSet.getInt(5);
			int facebookMeasurement = resultSet.getInt(6);
			double twitter_last10 = resultSet.getDouble(7);
			int twitter_last24h = resultSet.getInt(8);
			int twitter_last24hAuthorOnly = resultSet.getInt(9);
			long google_foundPages = resultSet.getLong(10);



			Measurement measurement = new Measurement(id, date, event, bingWeb, bingNews,
					facebookMeasurement, twitter_last10, twitter_last24h, twitter_last24hAuthorOnly, 
					google_foundPages);
			measurements.add(measurement);
		}
		statement.close();
		return measurements;

	}
	@Override
	public void updateMeasurementFacebook(Measurement measurement) throws SQLException {
		// PreparedStatements can use variables and are more efficient
		preparedStatement = connect
				.prepareStatement("update concertsdataset.measurements set facebook_measurement = ? "
						+ "where measurement_id = ?");

		// Parameters start with 1

		preparedStatement.setInt(1, measurement.getFacebook_measurement());
		preparedStatement.setInt(2, measurement.getId());

		preparedStatement.executeUpdate();
		statement.close();	
		
	}
	
	
	@Override
	public void updateMeasurementTwitterLast24Authors(Measurement measurement) throws SQLException {

		// PreparedStatements can use variables and are more efficient
		preparedStatement = connect
				.prepareStatement("update concertsdataset.measurements "
						+ "set twitter_last24hAuthorOnly = ? "
						+ "where measurement_id = ?");

		// Parameters start with 1

		preparedStatement.setInt(1, measurement.getTwitter_last24hAuthorOnly());
		preparedStatement.setInt(2, measurement.getId());

		preparedStatement.executeUpdate();
		statement.close();	
	}
	
	
	
	public static void main(String[] args) throws SQLException{
		Measurement measurement = new Measurement("28/19/2014",2222, 333333, 4444, 5555, 
				66666, 9999999, 888888, 555555);
		MeasurementDAOImpl daoImpl = new MeasurementDAOImpl();
		daoImpl.insertMeasurement(measurement);
	}

}
