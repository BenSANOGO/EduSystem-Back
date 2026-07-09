package com.eduapi.edusystem.dashboard.dashBoardDTO;

import com.eduapi.edusystem.classes.classResponseDTO.ClassResponseDTO;
import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GlobalDashboardDTO {

    private Long totalStudents;

    private Long newStudents;

    private Long validatedStudents;

    private Long pendingStudents;

    private Long rejectedStudents;

    private Long totalClasses;

    private Long totalPlaces;

    private Long availablePlaces;

    private double fillPercentage;

    private double monthlyPercentage;

    private double monthlyVariation;

    private List<StudentResponseDTO> studentsList;

    private List<StudentResponseDTO> newStudentsList;

    private List<ClassResponseDTO> classes;
}

