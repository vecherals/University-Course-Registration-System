package com.university.ui;

import java.util.Scanner;
import com.university.model.Course;
import com.university.model.Enrollment;
import com.university.model.Instructor;
import com.university.model.Section;
import com.university.model.Student;
import com.university.model.TimeSlot;
import com.university.repository.CourseRepository;
import com.university.repository.SectionRepository;
import com.university.repository.StudentRepository;
import com.university.service.GradingService; 
import com.university.service.RegistrationService;

public class ConsoleApp {
    private static StudentRepository studentRepo = new StudentRepository();
    private static CourseRepository courseRepo = new CourseRepository();
    private static SectionRepository sectionRepo = new SectionRepository();
    private static RegistrationService registrationService = new RegistrationService(studentRepo, courseRepo, sectionRepo);
    private static GradingService gradingService = new GradingService(sectionRepo);
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the university registration system");
        
        boolean running = true;
        while(running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Student Menu (Register/View/Drop)");
            System.out.println("2. Instructor Menu (View Roster/Grades)");
            System.out.println("3. Admin Menu (Create Data)");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            
            String input = scanner.nextLine();
            
            if (input.equals("1")) {
                handleStudentMenu(scanner);
            } else if (input.equals("2")) {
                handleInstructorMenu(scanner);
            } else if (input.equals("3")) {
                handleAdminMenu(scanner);
            } else if (input.equals("4")) {
                running = false;
                System.out.println("goodbye.");
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

  //student
    private static void handleStudentMenu(Scanner scanner) {
        System.out.print("Enter your Student ID: ");
        String studentId = scanner.nextLine();
        
        Student student = studentRepo.findById(studentId);
        if (student == null) {
            System.out.println("error: Student ID not found. Start with generating data in the admin menu");
            return;
        }
        System.out.println("Hello, " + student.getName());
        System.out.println("1. Register for a class");
        System.out.println("2. View my schedule");
        System.out.println("3. Drop a class");
        System.out.println("4. View transcript & GPA"); 
        System.out.print("Choose: ");
        String choice = scanner.nextLine();
        
        if(choice.equals("1")) {
            System.out.println("--- Available sections ---");
            for (Section s : sectionRepo.findAll()) {
                System.out.println(s.getSectionId() + " - " + s.getCourse().getTitle() + " (" + s.getAvailableSeats() + " seats left)");
            }
            
            System.out.print("Enter section ID to register: ");
            String sectionId = scanner.nextLine();
            
            String result = registrationService.register(studentId, sectionId);
            System.out.println(result);
            
        } else if (choice.equals("2")) {
            System.out.println("--- My Schedule ---");
            for (String courseCode : student.getEnrolledCourses()) {
                System.out.println(" - " + courseCode);
            }
        } else if (choice.equals("3")) {
            System.out.print("Enter section ID to drop: ");
            String sectionId = scanner.nextLine();
            String result = registrationService.drop(studentId, sectionId);
            System.out.println(result);
        } else if (choice.equals("4")) {
            viewTranscript(studentId);
        }
    }

    //instructor
    private static void handleInstructorMenu(Scanner scanner) {
        System.out.println("\n--- INSTRUCTOR LOGIN ---");
        System.out.print("Enter Instructor ID: ");
        String instructorId = scanner.nextLine();

        System.out.println("Welcome, Instructor " + instructorId);
        System.out.println("1. View my sections");
        System.out.println("2. View class roster");
        System.out.println("3. Post grade");
        System.out.print("Choose: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            System.out.println("--- My Assigned Sections ---");
            for (Section s : sectionRepo.findAll()) {
                if (s.getInstructor().getId().equals(instructorId)) {
                    System.out.println(" - " + s.getSectionId() + " (" + s.getCourse().getTitle() + ")");
                }
            }
        } else if (choice.equals("2")) {
            System.out.print("Enter Section ID: ");
            String sectionId = scanner.nextLine();
            Section section = sectionRepo.findById(sectionId);
            
            if (section != null && section.getInstructor().getId().equals(instructorId)) { //checking if section exists and if the insructor owns it
                System.out.println("--- Roster ---");
                if (section.getEnrollments().isEmpty()) {
                    System.out.println("(No students enrolled yet)");
                } else {
                    for (Enrollment e : section.getEnrollments()) {
                        String gradeInfo = (e.getGrade() == null) ? "(No Grade)" : "[" + e.getGrade() + "]"; //display name and grade
                        System.out.println(e.getStudent().getName() + " " + gradeInfo);
                    }
                }
            } else {
                System.out.println("Error: Section not found or access denied.");
            }
        } else if (choice.equals("3")) {
            System.out.print("Enter Section ID: ");
            String secId = scanner.nextLine();
            System.out.print("Enter Student ID: ");
            String stuId = scanner.nextLine();
            System.out.print("Enter Grade (A/B/C): ");
            String gr = scanner.nextLine();
            
            boolean success = gradingService.postGrade(instructorId, secId, stuId, gr);
            if (success) {
                System.out.println("Grade posted");
            } else {
                System.out.println("Failed to post grade");
            }
        }
    }

    //admin
    private static void handleAdminMenu(Scanner scanner) {
        System.out.println("\n--- ADMIN MENU ---");
        System.out.println("1. Generate sample data (Students/Courses)");
        System.out.println("2. View all sections");
        System.out.print("Choose: ");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            Student s1 = new Student("240201001", "James Bond", "james@edu.tr", "CE");
            Student s2 = new Student("240203001", "Gwen Stacy", "gwen@edu.tr", "Math");
            studentRepo.save(s1);
            studentRepo.save(s2);

            Instructor i1 = new Instructor("I1", "Dr. Watson", "watson@edu.tr", "CS");

            Course c1 = new Course("CS2001", "Object Oriented Programming", 5);
            Course c2 = new Course("MATH2003", "Differential Equations", 5);
            courseRepo.save(c1);
            courseRepo.save(c2);

            Section sec1 = new Section("CS2001-A", c1, i1, 2); 
            sec1.addTimeSlot(new TimeSlot("Monday", "10:00", "11:30", "B1-18"));
            
            Section sec2 = new Section("MATH2003-A", c2, i1, 30);
            sec2.addTimeSlot(new TimeSlot("Monday", "11:00", "12:30", "A2-92"));

            sectionRepo.save(sec1);
            sectionRepo.save(sec2);
            
            System.out.println("Sample data generated successfully");
            
        } else if (choice.equals("2")) {
            System.out.println("--- All Sections ---");
            for (Section s : sectionRepo.findAll()) {
                System.out.println(s.getSectionId() + " " + s.getCourse().getTitle());
            }
        }
    }
    //transcript and gpa
    private static void viewTranscript(String studentId) {
    	System.out.println("\n--- TRANSCRIPT ---");
    	
    	double totalGradePoints = 0;
    	int totalCredits = 0;
    	boolean hasGrades = false;
    	
    	for(Section section : sectionRepo.findAll()) { //looping through all sections to find where this student is enrolled
            for(Enrollment e : section.getEnrollments()) {
                if(e.getStudent().getId().equals(studentId)) {
        			String grade = e.getGrade();
        			String courseTitle = section.getCourse().getTitle();
        			int credits = section.getCourse().getCredits();
        			String displayGrade = (grade == null) ? "in progress" : grade;
        			System.out.println(section.getCourse().getCode() + " " + courseTitle + " [" + credits + " credits] -> " + displayGrade);
        			
        			if (grade != null) { //calculating the gpa
        				double points = 0;
        				switch (grade.toUpperCase()) {
        					case "A": points = 4.0; break;
        					case "B": points = 3.0; break;
        					case "C": points = 2.0; break;
        					case "D": points = 1.0; break;
        					case "F": points = 0.0; break;
        					default: points = 0.0;
        				}
        				totalGradePoints += (points * credits);
                        totalCredits += credits;
                        hasGrades = true;
        			}
        		}
            }
    	}
    
        System.out.println("-----------------------------");
        if (hasGrades && totalCredits > 0) {
            double gpa = totalGradePoints / totalCredits;
            System.out.printf("cumulative GPA: %.2f\n", gpa);
        } else {
            System.out.println("no graded courses yet");
        }
    } 
}