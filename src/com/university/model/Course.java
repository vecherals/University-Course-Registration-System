package com.university.model;
import java.util.ArrayList;
import java.util.List;

public class Course {
	private String code;
	private String title;
	private int credits;
	private List<String> prerequisites; //list of course codes that required before taking this one
	
	public Course(String code, String title, int credits) {
		this.code = code;
		this.title = title;
		this.credits = credits;
		this.prerequisites = new ArrayList<>();
	}
	
	public void addPrerequisite(String courseCode) {
		prerequisites.add(courseCode);
	}
	
	//getters
	public String getCode() {
		return code;
	}
	public String getTitle() {
		return title;
	}
	public int getCredits() {
		return credits;
	}
	public List<String> getPrerequisites() {
		return prerequisites;
	}
	
	@Override
	public String toString( ) {
		return code + ": " + title + " (" + credits + " credits)";
	}
}
