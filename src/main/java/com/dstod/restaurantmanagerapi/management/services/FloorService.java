package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.management.FloorDoesNotExistException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateFloorRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.FloorInfoDto;
import com.dstod.restaurantmanagerapi.management.models.dtos.UpdateFloorRequest;
import com.dstod.restaurantmanagerapi.management.models.entities.Floor;
import com.dstod.restaurantmanagerapi.management.repositories.FloorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.*;

@Service
public class FloorService {
    private final FloorRepository floorRepository;

    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    public SuccessResponse createFloor(CreateFloorRequest request) {

        Floor floor = mapToFloorEntity(request);

        Floor savedFloor = this.floorRepository.save(floor);

        FloorInfoDto savedFloorInfoDto = mapToFloorInfo(savedFloor);

        return new SuccessResponse("Successfully created floor", new Date(), savedFloorInfoDto);
    }

    public SuccessResponse createFloor() {
        Floor floor = new Floor(0L, getNextFloor(), NOT_AVAILABLE, new ArrayList<>());

        Floor savedFloor = this.floorRepository.save(floor);

        FloorInfoDto savedFloorInfoDto = mapToFloorInfo(savedFloor);

        return new SuccessResponse("Successfully created floor", new Date(), savedFloorInfoDto);
    }

    public SuccessResponse updateFloor(Long id, UpdateFloorRequest request) {
        Floor floor = this.floorRepository.findById(id).orElseThrow(() -> new FloorDoesNotExistException(String.format("Floor with id %d does not exist", id)));

        floor.setFloorName(request.floorName());

        Floor savedFloor = this.floorRepository.save(floor);

        FloorInfoDto floorInfoDto = mapToFloorInfo(savedFloor);

        return new SuccessResponse("Successfully updated floor", new Date(), floorInfoDto);
    }

    public void deleteFloor() {

    }

    public void getAllFloors() {

    }

    public void getFloorById() {

    }

    private FloorInfoDto mapToFloorInfo(Floor floorEntity) {
        return new FloorInfoDto(
                floorEntity.getId(),
                floorEntity.getFloorName(),
                floorEntity.getFloor()
        );
    }



    private Floor mapToFloorEntity(CreateFloorRequest request) {
        return new Floor(
                0L,
                getNextFloor(),
                !request.floorName().isEmpty() ? request.floorName() : NOT_AVAILABLE,
                new ArrayList<>()
        );
    }

    private Long getNextFloor() {
        return this.floorRepository.count() + 1;
    }




    protected Floor getFloor(Optional<Floor> floorByFloor) {
        return floorByFloor.orElseGet(() ->
                this.floorRepository
                        .findFloorByFloor(1)
                        .orElseGet(() -> this.floorRepository
                                .save(new Floor(0, 1, NOT_AVAILABLE, new ArrayList<>()))));
    }
}
