package com.university.model;

public class Enrollment {
    private Student student;
    private Section section;
    private String grade; 

    public Enrollment(Student student, Section section) {
        this.student = student;
        this.section = section;
        this.grade = null; //at first no grades
    }

    public Student getStudent() { return student; }
    public Section getSection() { return section; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}