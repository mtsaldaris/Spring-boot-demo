package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// Service Class for Student object
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        // Fetch a list of students from the database
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {

        // check if a student already exists with the user defined email
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        // if email already exists - throw new Exception
        if (studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);

        if (!studentExists) {
            throw new IllegalStateException(
                    "student with id" + studentId + "does not exist"
            );
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {

        // Check if a student exists with the ID passed - otherwise throw exception
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                "student wit id" + studentId + "does not exist"
        ));

        // check if new name is not null & is not the same as previous name
        // then set name with the new name given from request parameters
        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        // check if new email is not null & is not the same as previous email
        // then set email with the new email given from request parameters
        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {

            // also check if email is taken by other student

            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }

            student.setEmail(email);
        }
    }
}
