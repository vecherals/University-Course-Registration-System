package com.university.model;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
	private String major;
	private double gpa;
	private List<String> enrolledCourses; //to keep track of classes
	
	public Student(String id, String name, String email, String major) {
		super(id,name,email);
		this.major = major;
		this.gpa = 0.0;
		this.enrolledCourses = new ArrayList<>();
	}

	@Override
	public String getRole() {
		
		return "Student";
	}
	public String getMajor() {
		return major;
	}
	public double getGpa() {
		return gpa;
	}
	public List<String> getEnrolledCourses() {
		return enrolledCourses;
	}
	
	@Override 
	public String toString() {
		return super.toString() + " / Major: " + major + " / GPA: " + gpa;
	}

}
