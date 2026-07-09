package com.eduapi.edusystem.classes.classEntity;
import com.eduapi.edusystem.students.studentEntity.StudentEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "classes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long places;
    private Long availablePlaces;
    @OneToMany(mappedBy = "classes",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<StudentEntity> students=new ArrayList<>();
    private Date createdAt;
    private Date updatedAt;

}
