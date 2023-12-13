package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.management.SectionDoesNotExistException;
import com.dstod.restaurantmanagerapi.common.exceptions.management.SectionDuplicationException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.*;
import com.dstod.restaurantmanagerapi.management.models.entities.Floor;
import com.dstod.restaurantmanagerapi.management.models.entities.Section;
import com.dstod.restaurantmanagerapi.management.repositories.FloorRepository;
import com.dstod.restaurantmanagerapi.management.repositories.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.*;

@Service
public class SectionService {
    private final SectionRepository sectionRepository;
    private final FloorRepository floorRepository;

    private final TableService tableService;

    public SectionService(SectionRepository sectionRepository, FloorRepository floorRepository, TableService tableService) {
        this.sectionRepository = sectionRepository;
        this.floorRepository = floorRepository;
        this.tableService = tableService;
    }

    public SuccessResponse createSection(CreateSectionRequest request) {

        Optional<Floor> floorByFloor = this.floorRepository.findFloorByFloor(request.floor());
        Optional<Section> sectionBySectionName = sectionRepository.findBySectionName(request.sectionName());

        if (sectionBySectionName.isPresent()) {
            throw new SectionDuplicationException(String.format(SECTION_DUPLICATION, request.sectionName()));
        }

        Section section = createSectionEntity(request, floorByFloor);

        Section savedSection = this.sectionRepository.save(section);

        CreateSectionResponse savedSectionResponse = mapToSectionResponse(savedSection);

        return new SuccessResponse("Section successfully created", new Date(), savedSectionResponse);
    }

    public SuccessResponse updateSection(UpdateSectionRequest request, Long id) {
        this.sectionRepository.findBySectionNameExcludingId(request.sectionName(), id).ifPresent(e -> {
            throw new SectionDuplicationException(String.format(SECTION_DUPLICATION, request.sectionName()));
        });

        Section section = this.sectionRepository
                .findById(id)
                .orElseThrow(() -> new SectionDoesNotExistException(String.format("Section with id %d does not exist", id)));


        setSectionDetails(request, section);

        Section savedSection = this.sectionRepository.save(section);
        CreateSectionResponse sectionResponse = mapToSectionResponse(savedSection);

        return new SuccessResponse(SECTION_SUCCESSFULLY_UPDATED, new Date(), sectionResponse);
    }

    private void setSectionDetails(UpdateSectionRequest request, Section section) {
        section.setSectionName(request.sectionName());

        if (request.floor() != null) {
            Floor floor = getFloor(this.floorRepository.findFloorByFloor(request.floor()));
            section.setFloor(floor);
        }
        if (request.active() != null) {
            section.setActive(request.active());
        }
    }

    private CreateSectionResponse mapToSectionResponse(Section section) {
        return new CreateSectionResponse(
                section.getId(),
                section.getSectionName(),
                section.getFloor().getFloor(),
                section.isActive()
        );
    }

    private SectionInfoDto mapToSectionInfoDto(Section section) {
        List<TableInfoDto> tables = section
                .getTables()
                .stream()
                .map(tableService::mapToTableInfoDto)
                .toList();

        return new SectionInfoDto(
                section.getId(),
                section.getSectionName(),
                section.getFloor().getFloor(),
                section.isActive(),
                tables
        );
    }

    private Section createSectionEntity(CreateSectionRequest request, Optional<Floor> floorByFloor) {
        Floor floor = getFloor(floorByFloor);

        return new Section(
                0L,
                request.sectionName(),
                true,
                new ArrayList<>(),
                floor
        );
    }

    private Floor getFloor(Optional<Floor> floorByFloor) {
        return floorByFloor.orElseGet(() ->
                this.floorRepository
                        .findFloorByFloor(1)
                        .orElseGet(() -> this.floorRepository
                                .save(new Floor(0, 1, "N/A", new ArrayList<>()))));
    }
}
