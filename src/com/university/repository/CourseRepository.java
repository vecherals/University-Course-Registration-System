package com.university.repository;
import com.university.model.Course;

public class CourseRepository extends InMemoryRepository<Course> {
    @Override
    protected String getId(Course item) {
        return item.getCode();
    }
}