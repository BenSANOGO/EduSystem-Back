package com.eduapi.edusystem.classes.classRepository;

import com.eduapi.edusystem.classes.classEntity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<ClassEntity,Long> {

    public ClassEntity findByName(String name);

}
