package com.dash.leap.global.auth.jwt.controller;

import com.dash.leap.global.auth.dto.response.IdDuplicateResponse;
import com.dash.leap.global.auth.jwt.controller.docs.AuthControllerDocs;
import com.dash.leap.global.auth.dto.request.IdDuplicateRequest;
import com.dash.leap.global.auth.jwt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/register")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDocs {

    private final AuthService authService;

    @PostMapping("/verify")
    public ResponseEntity<IdDuplicateResponse> checkLoginIdDuplicate(
            @Valid @RequestBody IdDuplicateRequest request
    ) {
        IdDuplicateResponse response = authService.validateLoginId(request.loginId());
        return ResponseEntity.ok().body(response);
    }
}
