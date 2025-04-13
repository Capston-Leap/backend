package com.dash.leap.domain.information.controller;

import com.dash.leap.domain.information.controller.docs.InformationControllerDocs;
import com.dash.leap.domain.information.dto.response.InformationListResponse;
import com.dash.leap.domain.information.entity.enums.InfoType;
import com.dash.leap.domain.information.service.InformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/information")
@RequiredArgsConstructor
public class InformationController implements InformationControllerDocs {

    private final InformationService informationService;

    @GetMapping
    public ResponseEntity<InformationListResponse> readAllInformation(
            @RequestParam(name = "category", required = false) InfoType infoType,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize) {

        InformationListResponse response = informationService.getInformationList(infoType, pageNum, pageSize);
        return ResponseEntity.ok().body(response);
    }
}
