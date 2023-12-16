package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateFloorRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.FloorInfoDto;
import com.dstod.restaurantmanagerapi.management.models.dtos.UpdateFloorRequest;
import com.dstod.restaurantmanagerapi.management.services.FloorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
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

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateFloor(@RequestBody @Valid UpdateFloorRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(this.floorService.updateFloor(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorInfoDto> getFloor(@PathVariable Long id) {
        return ResponseEntity.ok(this.floorService.getFloorById(id));
    }

    @GetMapping
    public ResponseEntity<List<FloorInfoDto>> getAllFloors() {
        return ResponseEntity.ok(this.floorService.getAllFloors());
    }
}
