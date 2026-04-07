package com.university;
import com.university.model.Course;
import com.university.model.Instructor;
import com.university.model.Student;

public class TestRunner {

	public static void main(String[] args) {
		//a student
		Student s1 = new Student("240201001", "James Bond", "james@edu.tr", "Comp Eng");
		//an instructor
		Instructor i1 = new Instructor("100200300", "Dr. Strange", "strange@edu.tr", "Law");
		// a course
		Course c1 = new Course("CS2001", "Object Oriented Programming", 5);
		// printing them to check if toString() works;
		System.out.println("Testing");
		System.out.println(s1.toString());
        System.out.println(i1.toString());
        System.out.println(c1.toString());

	}

}
