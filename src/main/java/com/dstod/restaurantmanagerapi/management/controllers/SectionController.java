package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateSectionRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateSectionResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.TableInfoDto;
import com.dstod.restaurantmanagerapi.management.services.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/sections")
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createSection(@Valid @RequestBody CreateSectionRequest request,
                                                         UriComponentsBuilder uri) {
        SuccessResponse successResponse = sectionService.createSection(request);
        CreateSectionResponse savedSection = (CreateSectionResponse) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/tables/{id}").buildAndExpand(savedSection.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }
}
