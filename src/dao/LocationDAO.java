package dao;

import java.sql.SQLException;
import java.util.List;

import model.Location;

public interface LocationDAO {
	public List<Location> getLocations() throws SQLException;
	public Location getLocation(int id) throws SQLException;
	public Location getLocation(String name, String city, String place) throws SQLException;
	public void insertLocation(Location location) throws SQLException;
	public void deleteLocation(Location location) throws SQLException;
	public int getId(String nation, String city, String place) throws SQLException;

}
