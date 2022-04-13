package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.*;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student myron = new Student(
                    "Myron T",
                    "myron@gmail.com",
                    LocalDate.of(1995, MARCH, 10)
            );

            Student alex = new Student(
                    "Alex C",
                    "alex@gmail.com",
                    LocalDate.of(2000, AUGUST, 10)
            );

            repository.saveAll(
                    List.of(myron, alex)
            );
        };
    }

}
