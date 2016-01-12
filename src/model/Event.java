package model;


public class Event {

	private int id;
	private String name;
	private String date;
	private int location;
	private String artist;
	private double popularity;
	private String type;
	
	


	public Event(String name, String date, int location, String artist, double popularity, String type) {
		super();
		this.name = name;
		this.date = date;
		this.location = location;
		this.artist = artist;
		this.popularity = popularity;
		this.type = type;
	}




	public Event(int id, String name, String date, int location, String artist, double popularity, String type) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.location = location;
		this.artist = artist;
		this.popularity = popularity;
		this.type = type;
	}


	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public int getLocation() {
		return location;
	}


	public void setLocation(int location) {
		this.location = location;
	}


	public String getArtist() {
		return artist;
	}


	public void setArtist(String artist) {
		this.artist = artist;
	}


	public double getPopularity() {
		return popularity;
	}


	public void setPopularity(double popularity) {
		this.popularity = popularity;
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	


	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", date=" + date + ", location=" + location + ", artist=" + artist
				+ ", popularity=" + popularity + ", type=" + type + "]";
	}



	
	

}
