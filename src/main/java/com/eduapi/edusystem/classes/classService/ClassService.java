package com.eduapi.edusystem.classes.classService;

import com.eduapi.edusystem.classes.classEntity.ClassEntity;
import com.eduapi.edusystem.classes.classRepository.ClassRepository;
import com.eduapi.edusystem.classes.classResponseDTO.ClassResponseDTO;
import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
@Builder
public class ClassService {

    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository){
        this.classRepository=classRepository;
    }


    public String createClass(String name, Long places){

        ClassEntity existingClass = classRepository.findByName(name);

        if(existingClass == null){

            ClassEntity newClass = ClassEntity.builder()
                    .name(name)
                    .places(places)
                    .availablePlaces(places)
                    .createdAt(Date.from(Instant.now()))
                    .updatedAt(Date.from(Instant.now()))
                    .build();

            classRepository.save(newClass);

            return "Class created";
        }

        return "Class already exists";
    }



    public List<ClassResponseDTO> getAll(){
        List<ClassEntity> classes= classRepository.findAll();
        return classes.stream().map(clsses->ClassResponseDTO.builder()
                .id(clsses.getId())
                .name(clsses.getName())
                        .places(clsses.getPlaces())
                        .availablePlaces(clsses.getAvailablePlaces())
                .students(clsses.getStudents().stream().map(
                        student-> StudentResponseDTO.builder()
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

                ).toList()
        ).build()
        ).toList();
    }

    public List<ClassResponseDTO> getByClassId(Long id){
        Optional<ClassEntity> classes= classRepository.findById(id);
        return classes.stream().map(clsses->ClassResponseDTO.builder()
                .id(clsses.getId())
                .name(clsses.getName())
                .places(clsses.getPlaces())
                .availablePlaces(clsses.getAvailablePlaces())
                .students(clsses.getStudents().stream().map(
                                student-> StudentResponseDTO.builder()
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

                        ).toList()
                ).build()
        ).toList();
    }


    public String updateClass(Long id, String className, Long places) {

        Optional<ClassEntity> optional = classRepository.findById(id);

        if (optional.isEmpty()) {
            return "Class not found";
        }

        ClassEntity entity = optional.get();


        long numberOfStudents = entity.getStudents().size();

        if (places < numberOfStudents) {
            return "The number of places cannot be less than the number of enrolled students";
        }

        entity.setName(className);
        entity.setPlaces(places);

        entity.setAvailablePlaces(places - numberOfStudents);

        entity.setUpdatedAt(Date.from(Instant.now()));

        classRepository.save(entity);

        return "Class updated";
    }

    public String deleteClass(Long id){
        Optional<ClassEntity>optional=classRepository.findById(id);
        if(optional.isPresent()) {
            classRepository.deleteById(id);
            return "Class deleted";
        }else{
            return "Class not found";
        }


    }


}
