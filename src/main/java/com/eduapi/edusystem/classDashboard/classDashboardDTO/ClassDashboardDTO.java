package com.eduapi.edusystem.classDashboard.classDashboardDTO;

import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

    @Builder
    @Getter
    @Setter
    public class ClassDashboardDTO {

        private Long id;

        private String className;

        private Long places;

        private Long availablePlaces;

        private Long students;

        private Long newStudents;

        private Long pendingStudents;

        private double fillPercentage;

        private double monthlyPercentage;

        private double monthlyVariation;

        private List<StudentResponseDTO> studentsList;

        private List<StudentResponseDTO> newStudentsList;
    }


