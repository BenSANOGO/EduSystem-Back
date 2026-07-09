package com.eduapi.edusystem.students.studentResponseDTO;

import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class StudentResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String speciality;
    private String grade;
    private StudentStatus status;
    private String className;
    private Date enrollmenDate;
    private Date createdAt;
    private Date updatedAt;

}
