package com.dash.leap.global.auth.jwt.controller.docs;

import com.dash.leap.global.auth.dto.request.IdDuplicateRequest;
import com.dash.leap.global.auth.dto.response.IdDuplicateResponse;
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
            @ApiResponse(description = "아이디 사용 가능", responseCode = "200"),
            @ApiResponse(description = "아이디 사용 불가", responseCode = "400",
                        content = @Content(examples = @ExampleObject(value = """
                                {
                                  "status": "400 BAD_REQUEST",
                                  "message": "이미 존재하는 아이디입니다."
                                }
                                """)))
    })
    ResponseEntity<IdDuplicateResponse> checkLoginIdDuplicate(
            @Parameter(required = true) @Valid IdDuplicateRequest request
    );
}
