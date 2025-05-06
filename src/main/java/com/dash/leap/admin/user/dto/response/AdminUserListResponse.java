package com.dash.leap.admin.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "관리자용 사용자 정보 목록에 해당하는 응답입니다.")
public record AdminUserListResponse(

        @Schema(description = "회원 목록 리스트")
        List<AdminUserResponse> userList,

        @Schema(description = "현재 페이지 번호", example = "0")
        int pageNumber,

        @Schema(description = "페이지 사이즈", example = "10")
        int pageSize,

        @Schema(description = "전체 페이지 수", example = "3")
        int totalPages
) {

    public static AdminUserListResponse from(Page<AdminUserResponse> userPage) {
        return new AdminUserListResponse(userPage.getContent(), userPage.getNumber(), userPage.getSize(), userPage.getTotalPages());
    }
}
