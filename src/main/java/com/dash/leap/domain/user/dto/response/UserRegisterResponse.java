package com.dash.leap.domain.user.dto.response;

import com.dash.leap.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답입니다.")
public record UserRegisterResponse(

        @Schema(description = "회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "로그인 ID", example = "leapy@gachon.ac.kr")
        String loginId
) {

    public static UserRegisterResponse from(User user) {
        return new UserRegisterResponse(user.getId(), user.getLoginId());
    }
}
