package com.eduapi.edusystem.students.studentController;

import com.eduapi.edusystem.students.studentDTO.StudentDTO;
import com.eduapi.edusystem.students.studentEntity.StudentEntity;
import com.eduapi.edusystem.students.studentResponseDTO.StudentResponseDTO;
import com.eduapi.edusystem.students.studentService.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@Tag(name = "Students", description = "Students management APIs")

public class StudentController {

    public final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }






    @Operation(
            summary = "Create a new student",
            description = "Creates a new student and saves it into the database."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Student already exists")
    })
    @PostMapping("/add")
    public String post(@RequestBody StudentDTO input){
        return studentService.createStudent(input);
    }





    @Operation(
            summary = "Retrieve all students",
            description = "Returns the complete list of registered students."
    )
    @ApiResponse(responseCode = "200", description = "Students retrieved successfully")
    @GetMapping("/all")
    public List<StudentResponseDTO> get(){
        return studentService.getAll();
    }


    @GetMapping("/get/{id}")
    public StudentResponseDTO getById(@PathVariable Long id){
        return studentService.getStudent(id);
    }






    @Operation(
            summary = "Update student",
            description = "Updates an existing student's information."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @PutMapping("/update/{id}")
    public StudentDTO update(@PathVariable Long id,@RequestBody StudentDTO input){
        return studentService.updateStudent(id,input);
    }




    @Operation(summary = "Delete student")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Student deleted"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
