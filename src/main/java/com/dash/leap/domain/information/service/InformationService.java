package com.dash.leap.domain.information.service;

import com.dash.leap.domain.information.dto.response.InformationListResponse;
import com.dash.leap.domain.information.dto.response.InformationResponse;
import com.dash.leap.domain.information.entity.Information;
import com.dash.leap.domain.information.entity.enums.InfoType;
import com.dash.leap.domain.information.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;

    public InformationListResponse getInformationList(InfoType infoType, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Slice<Information> slice = informationRepository.findByInfoType(infoType, pageable);

        List<InformationResponse> responseList = slice.getContent().stream()
                .map(InformationResponse::new)
                .toList();

        return new InformationListResponse(responseList, slice.getNumber(), slice.getSize(), slice.hasNext());
    }
}