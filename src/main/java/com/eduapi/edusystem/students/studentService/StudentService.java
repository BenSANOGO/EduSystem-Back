package com.eduapi.edusystem.students.studentService;

import com.eduapi.edusystem.classes.classEntity.ClassEntity;
import com.eduapi.edusystem.classes.classRepository.ClassRepository;
import com.eduapi.edusystem.students.studentDTO.StudentDTO;
import com.eduapi.edusystem.students.studentEntity.StudentEntity;
import com.eduapi.edusystem.students.studentRepository.StudentRepository;
import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Builder
@Setter
@Getter
public class StudentService {

    public final StudentRepository studentRepository;
    public final ClassRepository classRepository;


    public StudentService(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }


    public String createStudent(StudentDTO dto) {

        ClassEntity studentClass = classRepository.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        if (studentClass.getAvailablePlaces() <= 0) {
            return "No available places in this class";
        }

        StudentEntity existingStudent = studentRepository.findByEmail(dto.getEmail());

        if (existingStudent != null) {
            return "Student already exists";
        }

        StudentEntity newStudent = StudentEntity.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .email(dto.getEmail())
                .speciality(dto.getSpeciality())
                .classes(studentClass)
                .grade(dto.getGrade())
                .status(dto.getStatus())
                .enrollmenDate(Date.from(Instant.now()))
                .createdAt(Date.from(Instant.now()))
                .updatedAt(Date.from(Instant.now()))
                .build();

        studentRepository.save(newStudent);

        studentClass.setAvailablePlaces(studentClass.getAvailablePlaces() - 1);
        classRepository.save(studentClass);

        return "Student created";
    }


    public List<StudentResponseDTO> getAll() {

        List<StudentEntity> students = studentRepository.findAll();

        return students.stream().map(

                student -> StudentResponseDTO
                        .builder()
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
                        .build()

        ).toList();


    }


    public StudentResponseDTO getStudent(Long id) {
        Optional<StudentEntity> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return null;
        }


        return student.stream().map(stdnt -> StudentResponseDTO.builder()
                .id(stdnt.getId())
                .firstName(stdnt.getFirstName())
                .lastName(stdnt.getLastName())
                .age(stdnt.getAge())
                .email(stdnt.getEmail())
                .speciality(stdnt.getSpeciality())
                .grade(stdnt.getGrade())
                .status(stdnt.getStatus())
                .className(stdnt.getClasses().getName())
                .enrollmenDate(stdnt.getEnrollmenDate())
                .createdAt(stdnt.getCreatedAt())
                .updatedAt(stdnt.getUpdatedAt())
                .build()

        ).findFirst().orElse(null);
    }


    public StudentDTO updateStudent(Long id, StudentDTO dto) {

        Optional<StudentEntity> optional = studentRepository.findById(id);

        if (optional.isEmpty()) {
            return null;
        }

        Optional<ClassEntity> classOptional = classRepository.findById(dto.getClassId());

        if (classOptional.isEmpty()) {
            return null;
        }

        StudentEntity student = optional.get();

        ClassEntity oldClass = student.getClasses();
        ClassEntity newClass = classOptional.get();

        if (!oldClass.getId().equals(newClass.getId())) {

            if (newClass.getAvailablePlaces() <= 0) {
                return null;
            }

            oldClass.setAvailablePlaces(oldClass.getAvailablePlaces() + 1);
            newClass.setAvailablePlaces(newClass.getAvailablePlaces() - 1);

            classRepository.save(oldClass);
            classRepository.save(newClass);

            student.setClasses(newClass);
        }

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setAge(dto.getAge());
        student.setEmail(dto.getEmail());
        student.setSpeciality(dto.getSpeciality());
        student.setGrade(dto.getGrade());
        student.setStatus(dto.getStatus());
        student.setUpdatedAt(Date.from(Instant.now()));

        studentRepository.save(student);

        return StudentDTO.builder()
                
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .age(student.getAge())
                .email(student.getEmail())
                .speciality(student.getSpeciality())
                .grade(student.getGrade())
                .status(student.getStatus())
                .classId(student.getClasses().getId())
                .build();
    }


    public String deleteStudent(Long id) {

        Optional<StudentEntity> optional = studentRepository.findById(id);

        if (optional.isEmpty()) {
            return "Student not found";
        }

        StudentEntity student = optional.get();

        ClassEntity studentClass = student.getClasses();

        studentClass.setAvailablePlaces(studentClass.getAvailablePlaces() + 1);
        classRepository.save(studentClass);

        studentRepository.delete(student);

        return "Student deleted";
    }
}



