package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateFloorRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.FloorInfoDto;
import com.dstod.restaurantmanagerapi.management.services.FloorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/floors")
public class FloorController {
    private final FloorService floorService;

    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createFloor(@RequestBody @Valid Optional<CreateFloorRequest> requestOpt,
                                                       UriComponentsBuilder uri) {
        SuccessResponse response;

        if (requestOpt.isPresent()) {
            response = this.floorService.createFloor(requestOpt.get());
        } else {
            response = this.floorService.createFloor();
        }

        FloorInfoDto savedFloor = (FloorInfoDto) response.savedObject();
        UriComponents uriComponents = uri.path("/floors/{id}").buildAndExpand(savedFloor.id());

        return ResponseEntity.created(uriComponents.toUri()).body(response);
    }
}
