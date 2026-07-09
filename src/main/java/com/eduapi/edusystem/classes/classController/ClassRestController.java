package com.eduapi.edusystem.classes.classController;
import com.eduapi.edusystem.classes.classDTO.ClassDTO;
import com.eduapi.edusystem.classes.classEntity.ClassEntity;
import com.eduapi.edusystem.classes.classResponseDTO.ClassResponseDTO;
import com.eduapi.edusystem.classes.classService.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/class")
public class ClassRestController {


    private final ClassService classService;


    public ClassRestController(ClassService classService){
        this.classService=classService;

    }


    @PostMapping("/add")
    public String post(@RequestBody ClassDTO.PostInput input){
        String response=classService.createClass(input.getName(),input.getPlaces());
        return response;
    }


    @GetMapping("/all")
    public List<ClassResponseDTO> get(){
        return classService.getAll();
    }

    @GetMapping("/get/{id}")
    public List<ClassResponseDTO> getById(@PathVariable Long id){
        return classService.getByClassId(id);
    }


    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id,@RequestBody ClassDTO.PostInput input){
        return classService.updateClass(id,input.getName(),input.getPlaces());
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        return classService.deleteClass(id);
    }

}
