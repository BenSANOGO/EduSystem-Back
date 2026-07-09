package com.eduapi.edusystem.dashboard.dashBoardController;

import com.eduapi.edusystem.dashboard.dashBoardDTO.GlobalDashboardDTO;
import com.eduapi.edusystem.dashboard.dashBoardService.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {


        private final DashboardService dashboardService;

        public DashboardController(DashboardService dashboardService){
            this.dashboardService = dashboardService;
        }


    @Operation(
            summary = "Retrieve all informations",
            description = "Returns the global informations."
    )
    @ApiResponse(responseCode = "200", description = "Informations retrieved successfully")
        @GetMapping("")
        public GlobalDashboardDTO globalDashboard(){
            return dashboardService.globalDashboard();
        }
    }

