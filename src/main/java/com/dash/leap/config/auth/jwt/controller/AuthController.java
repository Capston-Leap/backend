package com.dash.leap.config.auth.jwt.controller;

import com.dash.leap.config.auth.jwt.controller.docs.AuthControllerDocs;
import com.dash.leap.domain.user.dto.request.IdDuplicateRequest;
import com.dash.leap.config.auth.jwt.service.AuthService;
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
    public ResponseEntity<Boolean> checkLoginIdDuplicate(
            @Valid @RequestBody IdDuplicateRequest request
    ) {
        boolean validateLoginId = authService.validateLoginId(request.loginId());
        return ResponseEntity.ok().body(validateLoginId);
    }
}
