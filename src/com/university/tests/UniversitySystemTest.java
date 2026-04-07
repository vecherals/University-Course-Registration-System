package com.university.tests;

import com.university.model.*;
import com.university.repository.*;
import com.university.service.*;

public class UniversitySystemTest {

    public static void main(String[] args) {
        testRegisterSuccess();
        testRegisterStudentNotFound();
        testRegisterSectionNotFound();
        testRegisterClassFull();
        testRegisterAlreadyEnrolled();
        testDropSuccess();
        testDropNotEnrolled();
        testScheduleConflict();
        testPostGradeSuccess();
        testPostGradeWrongInstructor();
    }

    //test1
    private static void testRegisterSuccess() {
        System.out.print("Test 1, register success: ");
               
        StudentRepository sRepo = new StudentRepository();
        CourseRepository cRepo = new CourseRepository();
        SectionRepository secRepo = new SectionRepository();
        RegistrationService regService = new RegistrationService(sRepo, cRepo, secRepo);

        Student s1 = new Student("240201001", "James Bond", "james@edu.tr", "CE");
        sRepo.save(s1);
        Course c1 = new Course("CS2001", "Object Oriented Programming", 5);
        cRepo.save(c1);
        Section sec1 = new Section("CS2001-A", c1, new Instructor("I1", "Dr. Watson", "watson@edu.tr", "CS"), 2);
        secRepo.save(sec1);
        String result = regService.register("240201001", "CS2001-A");
        if (result.startsWith("success") && sec1.getAvailableSeats() == 1) {
            System.out.println("passed");
        } else {
            System.out.println("failed");
        }
    }

    //test2
    private static void testRegisterStudentNotFound() {
        System.out.print("Test 2, student not found: ");
        RegistrationService regService = new RegistrationService(new StudentRepository(), new CourseRepository(), new SectionRepository());
      
        String result = regService.register("999999999", "CS2001-A"); //random id that doesnt exist
        
        if (result.contains("student not found")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test3
    private static void testRegisterSectionNotFound() {
        System.out.print("Test 3, section not found: ");
        StudentRepository sRepo = new StudentRepository();
        sRepo.save(new Student("240201001", "James", "j@edu.tr", "CE"));
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), new SectionRepository());

        String result = regService.register("240201001", "invalid section");
        
        if (result.contains("section not found")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test4
    private static void testRegisterClassFull() {
        System.out.print("Test 4, class full: ");
        StudentRepository sRepo = new StudentRepository();
        SectionRepository secRepo = new SectionRepository();
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), secRepo);

        sRepo.save(new Student("240201001", "James", "j@edu.tr", "CE"));
        sRepo.save(new Student("240201002", "Gwen", "g@edu.tr", "CE"));
        sRepo.save(new Student("240201003", "Mary", "m@edu.tr", "CE"));

        Course c1 = new Course("CS2001", "OOP", 5);
        Section sec1 = new Section("CS2001-A", c1, new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS"), 2); //capacity of 2
        secRepo.save(sec1);

        regService.register("240201001", "CS2001-A");
        regService.register("240201002", "CS2001-A");
        
        String result = regService.register("240201003", "CS2001-A"); //third person

        if (result.equals("error: class is full")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test5
    private static void testRegisterAlreadyEnrolled() {
        System.out.print("Test 5, already enrolled: ");
        StudentRepository sRepo = new StudentRepository();
        SectionRepository secRepo = new SectionRepository();
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), secRepo);

        sRepo.save(new Student("240201001", "James", "j@edu.tr", "CE"));
        Section sec1 = new Section("CS2001-A", new Course("CS2001", "OOP", 5), new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS"), 10);
        secRepo.save(sec1);

        regService.register("240201001", "CS2001-A");
        String result = regService.register("240201001", "CS2001-A"); ///trying again

        if (result.contains("already enrolled")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test6
    private static void testDropSuccess() {
        System.out.print("Test 6, drop success: ");
        StudentRepository sRepo = new StudentRepository();
        SectionRepository secRepo = new SectionRepository();
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), secRepo);

        Student s1 = new Student("240201001", "James", "j@edu.tr", "CE");
        sRepo.save(s1);
        Section sec1 = new Section("CS2001-A", new Course("CS2001", "OOP", 5), new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS"), 10);
        secRepo.save(sec1);

        regService.register("240201001", "CS2001-A");
        String result = regService.drop("240201001", "CS2001-A");

        if (result.startsWith("success") && sec1.getAvailableSeats() == 10) System.out.println("passed");
        else System.out.println("failed");
    }

    //test7
    private static void testDropNotEnrolled() {
        System.out.print("Test 7, drop/not enrolled: ");
        StudentRepository sRepo = new StudentRepository();
        SectionRepository secRepo = new SectionRepository();
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), secRepo);

        sRepo.save(new Student("240201001", "James", "j@edu.tr", "CE"));
        Section sec1 = new Section("CS2001-A", new Course("CS2001", "OOP", 5), new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS"), 10);
        secRepo.save(sec1);

        String result = regService.drop("240201001", "CS2001-A");

        if (result.contains("not enrolled")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test8
    private static void testScheduleConflict() {
        System.out.print("Test 8, schedule conflict: ");
        StudentRepository sRepo = new StudentRepository();
        CourseRepository cRepo = new CourseRepository();
        SectionRepository secRepo = new SectionRepository();
        RegistrationService regService = new RegistrationService(sRepo, cRepo, secRepo);

        sRepo.save(new Student("240201001", "James", "j@edu.tr", "CE"));
        Instructor i1 = new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS");
        Course c1 = new Course("CS2001", "OOP", 5);
        cRepo.save(c1);
        Section sec1 = new Section("CS2001-A", c1, i1, 10);
        sec1.addTimeSlot(new TimeSlot("Monday", "10:00", "11:30", "B1-18"));
        secRepo.save(sec1);
        Course c2 = new Course("MATH2003", "Diff Eq", 5);
        cRepo.save(c2);
        Section sec2 = new Section("MATH2003-A", c2, i1, 10);
        sec2.addTimeSlot(new TimeSlot("Monday", "11:00", "12:30", "A2-92"));
        secRepo.save(sec2);

        regService.register("240201001", "CS2001-A");
        String result = regService.register("240201001", "MATH2003-A");

        if (result.contains("schedule conflict")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test9
    private static void testPostGradeSuccess() {
        System.out.print("Test 9, post grade: ");
        SectionRepository secRepo = new SectionRepository();
        StudentRepository sRepo = new StudentRepository();
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), secRepo);
        GradingService gradingService = new GradingService(secRepo);

        Student s1 = new Student("240201001", "James", "j@edu.tr", "CE");
        sRepo.save(s1);
        Section sec1 = new Section("CS2001-A", new Course("CS2001", "OOP", 5), new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS"), 10);
        secRepo.save(sec1);

        regService.register("240201001", "CS2001-A");
        
        boolean success = gradingService.postGrade("I1", "CS2001-A", "240201001", "A");

        String grade = sec1.getEnrollments().get(0).getGrade();

        if (success && grade.equals("A")) System.out.println("passed");
        else System.out.println("failed");
    }

    //test10
    private static void testPostGradeWrongInstructor() {
        System.out.print("Test 10, wrong instructor: ");
        SectionRepository secRepo = new SectionRepository();
        StudentRepository sRepo = new StudentRepository();
        RegistrationService regService = new RegistrationService(sRepo, new CourseRepository(), secRepo);
        GradingService gradingService = new GradingService(secRepo);

        Student s1 = new Student("240201001", "James", "j@edu.tr", "CE");
        sRepo.save(s1);
        Section sec1 = new Section("CS2001-A", new Course("CS2001", "OOP", 5), new Instructor("I1", "Dr. Watson", "w@edu.tr", "CS"), 10);
        secRepo.save(sec1);

        regService.register("240201001", "CS2001-A");
        
        boolean success = gradingService.postGrade("I2", "CS2001-A", "240201001", "A");//here dr strange tries to grade dr watson's class

        if (!success) System.out.println("passed");
        else System.out.println("failed");
    }
}