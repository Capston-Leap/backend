package com.dash.leap.admin.information.service;

import com.dash.leap.admin.community.exception.ForbiddenException;
import com.dash.leap.admin.information.dto.request.InformationCreateRequest;
import com.dash.leap.admin.information.dto.request.InformationUpdateRequest;
import com.dash.leap.admin.information.dto.response.*;
import com.dash.leap.domain.information.entity.Information;
import com.dash.leap.domain.information.repository.InformationRepository;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.global.auth.user.CustomUserDetails;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminInformationService {

    private final InformationRepository informationRepository;

    // 자립지원정보 목록 조회
    public Page<InformationListResponse> getInformationList(int page, int size) {
        log.info("[AdminInformationService] getInformationList() 실행: 자립지원정보 목록을 조회합니다: page = {}, size = {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Information> pageResult = informationRepository.findAll(pageable);

        return pageResult.map(info -> new InformationListResponse(
                info.getId(),
                info.getInfoType(),
                info.getTitle(),
                info.getContent(),
                info.getUrl(),
                info.getCreatedAt(),
                info.getUpdatedAt()
        ));
    }

    // 자립지원정보 상세 조회
    public InformationDetailResponse getInformationDetail(Long informationId) {
        log.info("[AdminInformationService] getInformationDetail() 실행: 자립지원정보를 상세 조회합니다: informationId = {}", informationId);

        Information information = informationRepository.findById(informationId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 자립지원정보입니다."));

        return new InformationDetailResponse(
                information.getId(),
                information.getInfoType(),
                information.getTitle(),
                information.getContent(),
                information.getUrl(),
                information.getCreatedAt(),
                information.getUpdatedAt()
        );
    }

    // 자립지원정보 생성
    @Transactional
    public InformationCreateResponse create(CustomUserDetails userDetails, InformationCreateRequest request) {
        log.info("[AdminInformationService] create() 실행: 새로운 자립지원정보를 생성합니다.");

        if (userDetails.user().getUserType() != UserType.ADMIN) {
            throw new ForbiddenException("관리자만 자립지원정보를 등록할 수 있습니다.");
        }

        Information information = Information.builder()
                .infoType(request.category())
                .title(request.title())
                .content(request.content())
                .url(request.url())
                .build();

        Information saved = informationRepository.save(information);

        return new InformationCreateResponse(
                saved.getId(),
                saved.getInfoType(),
                saved.getTitle(),
                saved.getContent(),
                saved.getUrl(),
                "자립지원정보가 성공적으로 등록되었습니다."
        );
    }

    // 자립지원정보 수정
    @Transactional
    public InformationUpdateResponse update(Long informationId, CustomUserDetails userDetails, InformationUpdateRequest request) {
        log.info("[AdminInformationService] update() 실행: 자립지원정보를 수정합니다: informationId = {}", informationId);

        Information information = informationRepository.findById(informationId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 자립지원정보입니다."));

        if (userDetails.user().getUserType() != UserType.ADMIN) {
            throw new ForbiddenException("관리자만 자립지원정보를 수정할 수 있습니다.");
        }

        information.update(
                request.category(),
                request.title(),
                request.content(),
                request.url()
        );

        return new InformationUpdateResponse(
                information.getId(),
                information.getInfoType(),
                information.getTitle(),
                information.getContent(),
                information.getUrl(),
                "자립지원정보가 성공적으로 수정되었습니다."
        );
    }

    // 자립지원정보 삭제
    @Transactional
    public InformationDeleteResponse delete(Long informationId, CustomUserDetails userDetails) {
        log.info("[AdminInformationService] delete() 실행: 자립지원정보를 삭제합니다: informationId = {}", informationId);

        Information information = informationRepository.findById(informationId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 자립지원정보입니다."));

        if (userDetails.user().getUserType() != UserType.ADMIN) {
            throw new ForbiddenException("관리자만 자립지원정보를 삭제할 수 있습니다.");
        }

        informationRepository.delete(information);

        return new InformationDeleteResponse(
                information.getId(),
                "자립지원정보가 성공적으로 삭제되었습니다.");
    }
}
