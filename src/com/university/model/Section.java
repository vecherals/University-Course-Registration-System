package com.university.model;
import java.util.ArrayList;
import java.util.List;

public class Section implements Schedulable {
    private String sectionId;
    private Course course;
    private Instructor instructor;
    private int capacity;
    private List<TimeSlot> schedule;
    private List<Enrollment> enrollments;
    
    public Section(String sectionId, Course course, Instructor instructor, int capacity) {
        this.sectionId = sectionId;
        this.course = course;
        this.instructor = instructor;
        this.capacity = capacity;
        this.schedule = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }
    
    public void addTimeSlot(TimeSlot slot) {
        schedule.add(slot);
    }
    
    @Override
    public List<TimeSlot> getMeetingTimes() {
        return schedule;
    }
    
    public boolean enroll(Student student) {
        if (enrollments.size() >= capacity) {
            return false;
        }
        // FIX: You must pass 'this' (the current section) to the Enrollment constructor
        Enrollment newEnrollment = new Enrollment(student, this);
        enrollments.add(newEnrollment);
        return true;
    }

    public String getSectionId() { return sectionId; }
    public Course getCourse() { return course; }
    public Instructor getInstructor() { return instructor; }
    public int getAvailableSeats() { return capacity - enrollments.size(); }
    public List<Enrollment> getEnrollments() { return enrollments; }

    @Override
    public String toString() {
        return sectionId + " (" + course.getCode() + ") - " + instructor.getName() + " [" + enrollments.size() + "/" + capacity + "]";
    }
}