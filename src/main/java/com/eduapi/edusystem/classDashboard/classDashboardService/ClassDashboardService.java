package com.eduapi.edusystem.classDashboard.classDashboardService;
import com.eduapi.edusystem.classDashboard.classDashboardDTO.ClassDashboardDTO;
import com.eduapi.edusystem.classes.classEntity.ClassEntity;
import com.eduapi.edusystem.classes.classRepository.ClassRepository;
import com.eduapi.edusystem.students.studentEntity.StudentEntity;
import com.eduapi.edusystem.students.studentRepository.StudentRepository;
import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassDashboardService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;


    /*
     * ============================
     * DASHBOARD PRINCIPAL
     * ============================
     */

    public ClassDashboardDTO classDashboard(Long classId){

        ClassEntity classe = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        return ClassDashboardDTO.builder()
                .id(classe.getId())
                .className(classe.getName())
                .places(classe.getPlaces())
                .availablePlaces(classe.getAvailablePlaces())

                .students(totalStudents(classId))
                .newStudents(newStudents(classId))
                .pendingStudents(pendingStudents(classId))

                .fillPercentage(fillPercentage(classId))
                .monthlyPercentage(monthlyPercentage(classId))
                .monthlyVariation(monthlyVariation(classId))

                .studentsList(getStudentsByClass(classId))
                .newStudentsList(getNewStudentsByClass(classId))

                .build();
    }


    /*
     * ============================
     * STATISTIQUES
     * ============================
     */

    public long totalStudents(Long classId){
        return studentRepository.countByClassesId(classId);
    }


    public long newStudents(Long classId){

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.withDayOfMonth(1).atStartOfDay();

        LocalDateTime end = today.plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        return studentRepository.countByClassesIdAndCreatedAtBetween(
                classId,
                start,
                end
        );
    }


    public long pendingStudents(Long classId){

        return studentRepository.countByClassesIdAndStatus(
                classId,
                StudentStatus.PENDING
        );
    }


    public double fillPercentage(Long classId){

        ClassEntity classe = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        long occupiedPlaces =
                classe.getPlaces() - classe.getAvailablePlaces();

        return (occupiedPlaces * 100.0) / classe.getPlaces();
    }


    public double monthlyPercentage(Long classId){

        ClassEntity classe = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        long currentMonthStudents = newStudents(classId);

        return (currentMonthStudents * 100.0) / classe.getPlaces();
    }


    public double monthlyVariation(Long classId){

        LocalDate today = LocalDate.now();

        LocalDateTime currentStart =
                today.withDayOfMonth(1).atStartOfDay();

        LocalDateTime currentEnd =
                today.plusMonths(1)
                        .withDayOfMonth(1)
                        .atStartOfDay();

        LocalDateTime previousStart =
                today.minusMonths(1)
                        .withDayOfMonth(1)
                        .atStartOfDay();

        LocalDateTime previousEnd =
                today.withDayOfMonth(1)
                        .atStartOfDay();

        long current = studentRepository.countByClassesIdAndCreatedAtBetween(
                classId,
                currentStart,
                currentEnd
        );

        long previous = studentRepository.countByClassesIdAndCreatedAtBetween(
                classId,
                previousStart,
                previousEnd
        );

        ClassEntity classe = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

        double currentPercent =
                (current * 100.0) / classe.getPlaces();

        double previousPercent =
                (previous * 100.0) / classe.getPlaces();

        return currentPercent - previousPercent;
    }


    /*
     * ============================
     * LISTES
     * ============================
     */

    public List<StudentResponseDTO> getStudentsByClass(Long classId){

        return studentRepository.findByClassesId(classId)
                .stream()
                .map(this::toDTO)
                .toList();
    }


    public List<StudentResponseDTO> getNewStudentsByClass(Long classId){

        LocalDate today = LocalDate.now();

        LocalDateTime start =
                today.withDayOfMonth(1).atStartOfDay();

        LocalDateTime end =
                today.plusMonths(1)
                        .withDayOfMonth(1)
                        .atStartOfDay();

        return studentRepository.findByClassesIdAndCreatedAtBetween(
                        classId,
                        start,
                        end
                )
                .stream()
                .map(this::toDTO)
                .toList();
    }


    /*
     * ============================
     * MAPPING DTO
     * ============================
     */

    private StudentResponseDTO toDTO(StudentEntity student){

        return StudentResponseDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .email(student.getEmail())
                .speciality(student.getSpeciality())
                .grade(student.getGrade())
                .status(student.getStatus())
                .className(student.getClasses().getName())
                .enrollmenDate(student.getEnrollmenDate())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }

}