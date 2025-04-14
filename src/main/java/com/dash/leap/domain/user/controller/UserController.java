package com.dash.leap.domain.user.controller;

import com.dash.leap.domain.user.controller.docs.UserControllerDocs;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        UserRegisterResponse registerResponse = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }
}
