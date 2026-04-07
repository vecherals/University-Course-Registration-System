package com.university.service;
import com.university.model.Enrollment;
import com.university.model.Section;
import com.university.model.Student;
import com.university.repository.SectionRepository;
import com.university.repository.StudentRepository;

public class GradingService {
    private SectionRepository sectionRepo;

    public GradingService(SectionRepository sectionRepo) {
        this.sectionRepo = sectionRepo;
    }

    public boolean postGrade(String instructorId, String sectionId, String studentId, String grade) {
        Section section = sectionRepo.findById(sectionId);
        
        if (section == null) return false;

        
        if (!section.getInstructor().getId().equals(instructorId)) { ///ensuring the instructor owns this section
            System.out.println("Error: You are not the instructor for this section.");
            return false;
        }

       
        for (Enrollment e : section.getEnrollments()) { //find the enrollment for this student
            if (e.getStudent().getId().equals(studentId)) {
                e.setGrade(grade); //assign the grade
                return true;
            }
        }

        System.out.println("error: Student not found in this section.");
        return false;
    }
}