package com.dash.leap.admin.user.controller;

import com.dash.leap.admin.user.dto.response.AdminDashboardResponse;
import com.dash.leap.admin.user.service.AdminDashboardService;
import com.dash.leap.admin.user.controller.docs.AdminDashboardControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard")
public class AdminDashboardController implements AdminDashboardControllerDocs {

    private final AdminDashboardService adminDashboardService;

    @GetMapping
    public ResponseEntity<AdminDashboardResponse> getDashboard() {
        return ResponseEntity.ok(adminDashboardService.getDashboardData());
    }
}
