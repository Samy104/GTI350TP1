package gti350.slalom.models;

import gti350.slalom.utils.TimeFormat;

public class Contestant {
	private static int currBib = 0;
	
	private String firstName;
	private String lastName;
	private String country;
	private int bib;
	private long bestTime = -1;
	private int runsLeft = 2;
	//Commit
	public Contestant(String lastName, String firstName, String country) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.country = country;

		currBib++;
		this.bib = currBib;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getCountry() {
		return country;
	}
	
	public int getBib() {
		return bib;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setBib(int bib) {
		this.bib = bib;
	}
	
	public void setTime(long time) {
		if (!hasBestTime()) {
			this.bestTime = time;
		}
		else if (time < this.bestTime) {
				this.bestTime = time;
		}
	}
	
	public long getBestTime() {
		return this.bestTime;
	}
	
	public int getRunsLeft() {
		return this.runsLeft;
	}
	
	public void decreaseRunsLeft() {
		if (this.runsLeft >= 1) {
			this.runsLeft--;
		}
	}
	
	public boolean hasBestTime() {
		return (this.bestTime != -1);
	}
	
	@Override
	public String toString() {
		String s = this.lastName + ", " + this.firstName + " #" + this.bib + " (" + this.country + ")";
		
		if (hasBestTime()) {
			TimeFormat tf = new TimeFormat();
			s += " - " + tf.format((this.bestTime));
		}
		
		return s; 
	}
}
