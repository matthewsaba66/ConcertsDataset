package model;

import com.mysql.jdbc.log.Log;

public class Measurement {
	
	private int id;
	private String date;
	private int event;
	private int bing_web;
	private int bing_news;
	private int facebook_measurement;
	private double twitter_last10;
	private int twitter_last24h;
	private int twitter_last24hAuthorOnly;
	private long google_foundPages;
	private long google_foundNewsPages;
	
	
	
	public Measurement(int id, String date, int event, int bing_web, int bing_news, 
			int facebook_measurement, double twitter_measurement, int twitter_last24h,
			int twitter_last24hAuthorOnly, long google_foundPages, long google_foundNewsPages) {
		super();
		this.id = id;
		this.date = date;
		this.event = event;
		this.bing_web = bing_web;
		this.bing_news = bing_news;
		this.facebook_measurement = facebook_measurement;
		this.twitter_last10 = twitter_measurement;
		this.twitter_last24h = twitter_last24h;
		this.twitter_last24hAuthorOnly = twitter_last24hAuthorOnly;
		this.google_foundPages = google_foundPages;
		this.google_foundNewsPages = google_foundNewsPages;
	}
	public Measurement(String date, int event, int bing_web, int bing_news,
			int facebook_measurement, double twitter_last10, int twitter_last24h, 
			int twitter_last24hAuthorOnly, long google_foundPages, long google_foundNewsPages) {
		super();
		this.date = date;
		this.event = event;
		this.bing_web = bing_web;
		this.bing_news = bing_news;
		this.facebook_measurement = facebook_measurement;
		this.twitter_last10 = twitter_last10;
		this.twitter_last24h = twitter_last24h;
		this.twitter_last24hAuthorOnly = twitter_last24hAuthorOnly;
		this.google_foundPages = google_foundPages;
		this.google_foundNewsPages = google_foundNewsPages;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getEvent() {
		return event;
	}
	public void setEvent(int event) {
		this.event = event;
	}
	public int getBing_web() {
		return bing_web;
	}
	public void setBing_web(int bing_web) {
		this.bing_web = bing_web;
	}
	public int getBing_news() {
		return bing_news;
	}
	public void setBing_news(int bing_news) {
		this.bing_news = bing_news;
	}
	public int getFacebook_measurement() {
		return facebook_measurement;
	}
	public void setFacebook_measurement(int facebook_measurement) {
		this.facebook_measurement = facebook_measurement;
	}
	public double getTwitter_last10() {
		return twitter_last10;
	}
	public void setTwitter_last10(double twitter_last10) {
		this.twitter_last10 = twitter_last10;
	}
	public int getTwitter_last24h() {
		return twitter_last24h;
	}
	public void setTwitter_last24h(int twitter_last24h) {
		this.twitter_last24h = twitter_last24h;
	}
	public int getTwitter_last24hAuthorOnly() {
		return twitter_last24hAuthorOnly;
	}
	public void setTwitter_last24hAuthorOnly(int twitter_last24hAuthorOnly) {
		this.twitter_last24hAuthorOnly = twitter_last24hAuthorOnly;
	}
	public long getGoogle_foundPages() {
		return google_foundPages;
	}
	public void setGoogle_foundPages(long google_foundPages) {
		this.google_foundPages = google_foundPages;
	}
	public long getGoogle_foundNewsPages() {
		return google_foundNewsPages;
	}
	public void setGoogle_foundNewsPages(long google_foundNewsPages) {
		this.google_foundNewsPages = google_foundNewsPages;
	}
	@Override
	public String toString() {
		return "Measurement [id=" + id + ", date=" + date + ", event=" + event + ", bing_web=" + bing_web
				+ ", bing_news=" + bing_news + ", facebook_measurement=" + facebook_measurement + ", twitter_last10="
				+ twitter_last10 + ", twitter_last24h=" + twitter_last24h + ", twitter_last24hAuthorOnly="
				+ twitter_last24hAuthorOnly + ", google_foundPages=" + google_foundPages + ", google_foundNewsPages="
				+ google_foundNewsPages + "]";
	}





	
	
	

}
