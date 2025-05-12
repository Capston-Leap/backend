package com.dash.leap.domain.home.controller;

import com.dash.leap.domain.home.controller.docs.HomeControllerDocs;
import com.dash.leap.domain.home.dto.response.HomeResponse;
import com.dash.leap.domain.home.service.HomeService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController implements HomeControllerDocs {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<HomeResponse> readHome(@AuthenticationPrincipal CustomUserDetails userDetails) {
        HomeResponse response = homeService.getHome(userDetails.user());
        return ResponseEntity.ok(response);
    }
}
