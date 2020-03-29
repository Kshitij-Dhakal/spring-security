package com.example.Spring.Security.Tutorial.controller;

import com.example.Spring.Security.Tutorial.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Kshitij Dhakal"),
            new Student(2, "Subin Shrestha"),
            new Student(3, "Subash Aryal"),
            new Student(4, "Lokesh Gaire"),
            new Student(5, "Dipak Mahato")
    );

    @GetMapping(path = "{id}")
    public Student getStudentById(@PathVariable("id") long id) {
        return STUDENTS.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student id doesn't exists!"));
    }

}
