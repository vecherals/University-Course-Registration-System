package com.university.repository;
import com.university.model.Student;

public class StudentRepository extends InMemoryRepository<Student> {
    @Override
    protected String getId(Student item) {
        return item.getId();
    }
}