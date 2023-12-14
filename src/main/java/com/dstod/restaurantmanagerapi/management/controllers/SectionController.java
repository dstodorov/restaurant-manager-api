package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.*;
import com.dstod.restaurantmanagerapi.management.services.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sections")
@Tag(name = "Manage sections APIs")
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @Operation(summary = "Create new section")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created section"),
            @ApiResponse(responseCode = "403", description = "Not authenticated")
    }
    )
    @PostMapping
    public ResponseEntity<SuccessResponse> createSection(@Valid @RequestBody CreateSectionRequest request,
                                                         UriComponentsBuilder uri) {
        SuccessResponse successResponse = sectionService.createSection(request);
        CreateSectionResponse savedSection = (CreateSectionResponse) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/tables/{id}").buildAndExpand(savedSection.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }

    @Operation(summary = "Update section")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateSection(@Valid @RequestBody UpdateSectionRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(this.sectionService.updateSection(request, id));
    }

    @Operation(summary = "Get all sections")
    @GetMapping
    public ResponseEntity<List<SectionInfoDto>> getAllSections() {
        return ResponseEntity.ok(sectionService.getAllSections());
    }

    @Operation(summary = "Get section by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<SectionInfoDto> getSection(@PathVariable Long id) {
        return ResponseEntity.ok(this.sectionService.getSectionInfo(id));
    }

}
