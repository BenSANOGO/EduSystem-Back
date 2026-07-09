package com.eduapi.edusystem.students.studentEntity;
import com.eduapi.edusystem.classes.classEntity.ClassEntity;

import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "students")

public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String speciality;
    //private String className;
    private String grade;
    private Date enrollmenDate;
    @Enumerated(EnumType.STRING)
    private StudentStatus status;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity classes;
    private Date createdAt;
    private Date updatedAt;

}
