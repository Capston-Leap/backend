package com.dash.leap.global.auth.jwt.controller.docs;

import com.dash.leap.domain.user.dto.request.IdDuplicateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "Auth API")
public interface AuthControllerDocs {

    @Operation(summary = "아이디 중복 검사")
    @ApiResponses(value = {
            @ApiResponse(description = "아이디 사용 가능", responseCode = "200", content = @Content(examples = @ExampleObject(value = "false"))),
            @ApiResponse(description = "아이디 사용 불가", responseCode = "400", content = @Content(examples = @ExampleObject(value = "true")))
    })
    ResponseEntity<Boolean> checkLoginIdDuplicate(
            @Parameter(required = true) @Valid IdDuplicateRequest request
    );
}
