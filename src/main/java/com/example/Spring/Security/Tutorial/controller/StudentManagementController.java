package com.example.Spring.Security.Tutorial.controller;

import com.example.Spring.Security.Tutorial.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Kshitij Dhakal"),
            new Student(2, "Subin Shrestha"),
            new Student(3, "Subash Aryal"),
            new Student(4, "Lokesh Gaire"),
            new Student(5, "Dipak Mahato")
    );

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMIN_TRAINEE')")
    public Student getStudentById(@PathVariable("id") long id) {
        return STUDENTS.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Student id doesn't exists!"));
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('course:write')")
    public List<Student> getAllStudents() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public Student registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
        return student;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("id") int id) {
        System.out.println(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@PathVariable("id") int id, @RequestBody Student student) {
        System.out.println(String.format("%s %s", student.getId(), student.getName()));
    }

}
