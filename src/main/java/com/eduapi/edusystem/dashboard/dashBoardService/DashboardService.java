package com.eduapi.edusystem.dashboard.dashBoardService;
import com.eduapi.edusystem.classes.classEntity.ClassEntity;
import com.eduapi.edusystem.classes.classRepository.ClassRepository;
import com.eduapi.edusystem.classes.classResponseDTO.ClassResponseDTO;
import com.eduapi.edusystem.dashboard.dashBoardDTO.GlobalDashboardDTO;
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
public class DashboardService {

    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;


    /*
     * ============================
     * DASHBOARD PRINCIPAL
     * ============================
     */

    public GlobalDashboardDTO globalDashboard(){

        return GlobalDashboardDTO.builder()

                .totalStudents(numberOfStudents())
                .newStudents(countStudentsByMonth())

                .validatedStudents(totalStudentsValidated())
                .pendingStudents(totalStudentsWaiting())
                .rejectedStudents(totalStudentsRejected())

                .totalClasses(totalClasses())

                .totalPlaces(totalPlaces())
                .availablePlaces(availablePlaces())

                .fillPercentage(globalFillPercentage())
                .monthlyPercentage(globalMonthlyPercentage())
                .monthlyVariation(globalMonthlyVariation())

                .studentsList(getAllStudents())
                .newStudentsList(newStudentsList())
                .classes(getAllClasses())

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


    public long totalClasses(){
        return classRepository.count();
    }

    public long totalPlaces() {

        return classRepository.findAll()
                .stream()
                .mapToLong(ClassEntity::getPlaces)
                .sum();
    }

    public long numberOfStudents() {
        return studentRepository.count();
    }

    public long availablePlaces(){

        return classRepository.findAll()
                .stream()
                .mapToLong(ClassEntity::getAvailablePlaces)
                .sum();
    }

    public long countStudentsByMonth() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.withDayOfMonth(1).atStartOfDay();

        LocalDateTime end = today.plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        return studentRepository.countByCreatedAtBetween(start, end);
    }

    public long totalStudentsValidated() {
        return studentRepository.countByStatus(StudentStatus.VALIDATED);
    }

    public long totalStudentsWaiting() {
        return studentRepository.countByStatus(StudentStatus.PENDING);
    }

    public long totalStudentsRejected() {
        return studentRepository.countByStatus(StudentStatus.REJECTED);
    }

    public List<StudentResponseDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    public List<StudentResponseDTO> newStudentsList() {

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.withDayOfMonth(1).atStartOfDay();

        LocalDateTime end = today.plusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();

        return studentRepository.findByCreatedAtBetween(start, end)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ClassResponseDTO> getAllClasses() {

        return classRepository.findAll()
                .stream()
                .map(classe -> ClassResponseDTO.builder()
                        .id(classe.getId())
                        .name(classe.getName())
                        .places(classe.getPlaces())
                        .availablePlaces(classe.getAvailablePlaces())
                        .build())
                .toList();
    }


    public double globalFillPercentage(){

        long totalPlaces = totalPlaces();

        long availablePlaces = availablePlaces();

        long occupied = totalPlaces - availablePlaces;

        return (occupied * 100.0) / totalPlaces;
    }


    public double globalMonthlyPercentage(){

        long students = countStudentsByMonth();

        return (students * 100.0) / totalPlaces();
    }


    public double globalMonthlyVariation(){

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

        long current =
                studentRepository.countByCreatedAtBetween(
                        currentStart,
                        currentEnd
                );

        long previous =
                studentRepository.countByCreatedAtBetween(
                        previousStart,
                        previousEnd
                );

        double currentPercent =
                (current * 100.0) / totalPlaces();

        double previousPercent =
                (previous * 100.0) / totalPlaces();

        return currentPercent - previousPercent;
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