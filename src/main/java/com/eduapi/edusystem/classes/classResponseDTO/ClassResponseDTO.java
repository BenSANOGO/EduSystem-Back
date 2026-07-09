package com.eduapi.edusystem.classes.classResponseDTO;

import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ClassResponseDTO {
    private Long id;
    private String name;
    private Long places;
    private Long availablePlaces;
    private List<StudentResponseDTO> students;
}
