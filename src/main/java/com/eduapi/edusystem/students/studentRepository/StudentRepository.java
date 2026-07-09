package com.eduapi.edusystem.students.studentRepository;

import com.eduapi.edusystem.students.studentEntity.StudentEntity;
import com.eduapi.edusystem.students.studentStatus.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {

    public StudentEntity findByEmail(String email);

    Long countByCreatedAtBetween(LocalDateTime start,LocalDateTime end);

    List<StudentEntity> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    long countByStatus(StudentStatus status);

    List<StudentEntity> findByClassesId(Long classId);

    List<StudentEntity> findByClassesIdAndCreatedAtBetween(
            Long classId,
            LocalDateTime start,
            LocalDateTime end
    );

    long countByClassesId(Long classId);

    long countByClassesIdAndStatus(Long classId, StudentStatus status);

    long countByClassesIdAndCreatedAtBetween(
            Long classId,
            LocalDateTime start,
            LocalDateTime end
    );






}
