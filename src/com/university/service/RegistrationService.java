package com.university.service;
import java.util.List;
import com.university.model.Section;
import com.university.model.Student;
import com.university.model.Enrollment; 
import com.university.repository.CourseRepository;
import com.university.repository.SectionRepository;
import com.university.repository.StudentRepository;

public class RegistrationService {
    private StudentRepository studentRepo;
    private CourseRepository courseRepo;
    private SectionRepository sectionRepo;
    private ScheduleConflictChecker conflictChecker;
    
    public RegistrationService(StudentRepository sRepo, CourseRepository cRepo, SectionRepository secRepo) {
        this.studentRepo = sRepo;
        this.courseRepo = cRepo;
        this.sectionRepo = secRepo;
        this.conflictChecker = new ScheduleConflictChecker();
    }

    public String register(String studentId, String sectionId) {
        Student student = studentRepo.findById(studentId); //find the student manually
        Section newSection = sectionRepo.findById(sectionId); // find the section manually
        
        if(student == null) {
            return "error: student not found";
        }
        
        if (newSection == null) {
            return "error: section not found";
        }
        
        for (String enrolledCourseCode : student.getEnrolledCourses()) { //loop through the list of string the student has
            if (enrolledCourseCode.equals(newSection.getCourse().getCode())) { //"if the code they have matches the one they want to take"
                return "error: student already enrolled in this course"; 
            }
        }
        
        if (newSection.getAvailableSeats() <= 0) { 
            return "error: class is full";
        }
        
        List<Section> allSections = sectionRepo.findAll(); //looking for all sections to find the ones this student is in
        
        for (Section existingSection : allSections) {
            boolean isStudentInClass = false; //checking if the student is in this sections enrollment list
            for (int i = 0; i < existingSection.getEnrollments().size(); i++) {
                if (existingSection.getEnrollments().get(i).getStudent().getId().equals(studentId)) {
                    isStudentInClass = true;
                }
            }
            
            if (isStudentInClass) { //if they are in his class, check for time conflict with the new class
                if (conflictChecker.hasConflict(existingSection, newSection)) {
                    return "error: schedule conflict with " + existingSection.getSectionId();
                }
            }
        }
        boolean success = newSection.enroll(student); //if success, enroll the student
        if (success) {
            student.getEnrolledCourses().add(newSection.getCourse().getCode()); //add the course code to the students personal list
            return "success: enrolled in " + newSection.getSectionId();
        } else {
            return "error: could not enroll";
        }
    }

    public String drop(String studentId, String sectionId) {
        Student student = studentRepo.findById(studentId);
        Section section = sectionRepo.findById(sectionId);

        if (student == null) {
            return "Error: Student not found";
        }
        if (section == null) {
            return "Error: Section not found";
        }

        boolean hasCourse = false; //checking if the student has this course in their personal list
        String courseCode = section.getCourse().getCode();
      
        for (String code : student.getEnrolledCourses()) {  //looping to check if they have the course string
            if (code.equals(courseCode)) {
                hasCourse = true;
                break;
            }
        }

        if (!hasCourse) {
            return "error: you are not enrolled in this course";
        }
        
        student.getEnrolledCourses().remove(courseCode); //remove the course code from the students personal list

        Enrollment enrollmentToRemove = null;
        
        for (int i = 0; i < section.getEnrollments().size(); i++) { //finding the enrollment object first then remove it
            Enrollment e = section.getEnrollments().get(i);
            if (e.getStudent().getId().equals(studentId)) {
                enrollmentToRemove = e;
                break; //found it, stop looping
            }
        }

        boolean removed = false;
        if (enrollmentToRemove != null) {
            section.getEnrollments().remove(enrollmentToRemove);
            removed = true;
        }

        if (removed) {
            return "success: dropped " + sectionId;
        } else {
            return "success: dropped " + sectionId + " (student was not in section roster)";
        }
    }
} 