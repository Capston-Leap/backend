package com.dash.leap.admin.user.dto.response;

import com.dash.leap.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "관리자 회원가입 응답입니다.")
public record AdminRegisterResponse(

        @Schema(description = "관리자 회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "로그인 ID", example = "admin@gachon.ac.kr")
        String loginId
) {

    public static AdminRegisterResponse from(User user) {
        return new AdminRegisterResponse(user.getId(), user.getLoginId());
    }
}
