package com.university.model;

public class Instructor extends Person {
	private String department;
	
	public Instructor(String id, String name, String email, String department) {
		super(id, name, email);
		this.department = department;
	}

	@Override
	public String getRole() {
		
		return "Instructor";
	}
	
	public String getDepartment() {
		return department;
	}
	
	@Override 
	public String toString() {
		return super.toString() + " / Department:" + department;
	}

}
