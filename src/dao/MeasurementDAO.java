package dao;

import java.sql.SQLException;
import java.util.List;

import model.Measurement;

public interface MeasurementDAO {
	public List<Measurement> getMeasurements() throws SQLException;
	public List<Measurement> getMeasurementsForEvent(int eventId) throws SQLException;
	public Measurement getMeasurement(int id) throws SQLException;
	public void insertMeasurement(Measurement measurement) throws SQLException;
	public void deleteMeasurement(Measurement measurement) throws SQLException;
	public Measurement getMeasurement(String date, int event) throws SQLException;
	public void updateMeasurementTwitterLast24(Measurement measurement) throws SQLException;
	public void updateMeasurementFacebook(Measurement measurement) throws SQLException;
	void updateMeasurementTwitterLast24Authors(Measurement measurement) throws SQLException;
}
