package com.eduapi.edusystem.classDashboard.classDashboardController;

import com.eduapi.edusystem.classDashboard.classDashboardDTO.ClassDashboardDTO;
import com.eduapi.edusystem.classDashboard.classDashboardService.ClassDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/dashboard")
public class ClassDashboardController {


        private final ClassDashboardService classDashboardService;

        public ClassDashboardController(ClassDashboardService classDashboardService){
            this.classDashboardService = classDashboardService;
        }


    @Operation(
            summary = "Retrieve all informations",
            description = "Returns the complete statistic of classes."
    )
    @ApiResponse(responseCode = "200", description = "Informations retrieved successfully")
        @GetMapping("/{id}")
        public ClassDashboardDTO classDashboard(@PathVariable Long id){
            return classDashboardService.classDashboard(id);
        }
    }

