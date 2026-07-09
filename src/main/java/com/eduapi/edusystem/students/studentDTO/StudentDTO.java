package com.eduapi.edusystem.students.studentDTO;

import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class StudentDTO {
        @Schema(example = "John")
        String firstName;
        @Schema(example = "Doe")
        String lastName;
        @Schema(example = "22")
        Integer age;
        @Schema(example = "john@gmail.com")
        String email;
        @Schema(example = "Software Engineering")
        String speciality;
        @Schema(example = "Senoir")
        String grade;
        @Schema(
                description = "Student status",
                allowableValues = {"PENDING", "VALIDATED", "REJECTED"},
                example = "PENDING"
        )
        StudentStatus status;
        @Schema(
                description = "Class identifier",
                example = "1"
        )
        Long classId;
}
