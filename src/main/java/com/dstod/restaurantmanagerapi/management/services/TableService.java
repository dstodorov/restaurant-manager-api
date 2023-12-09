package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.management.SectionDoesNotExistException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateTableRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.TableInfoDto;
import com.dstod.restaurantmanagerapi.management.models.entities.RTable;
import com.dstod.restaurantmanagerapi.management.models.entities.Section;
import com.dstod.restaurantmanagerapi.management.repositories.SectionRepository;
import com.dstod.restaurantmanagerapi.management.repositories.TableRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TableService {
    private final TableRepository tableRepository;
    private final SectionRepository sectionRepository;

    public TableService(TableRepository tableRepository, SectionRepository sectionRepository) {
        this.tableRepository = tableRepository;
        this.sectionRepository = sectionRepository;
    }

    public SuccessResponse createTable(CreateTableRequest request) {
        Section section = this.sectionRepository
                .findBySectionName(request.section())
                .orElseThrow(() -> new SectionDoesNotExistException(String.format("Section with name %s, does not exist.", request.section())));

        RTable table = mapToRTable(request, section);
        RTable savedTable = this.tableRepository.save(table);

        TableInfoDto savedTableInfoDto = mapToTableInfoDto(savedTable);

        return new SuccessResponse("Successfully created table", new Date(), savedTableInfoDto);

    }

    private RTable mapToRTable(CreateTableRequest request, Section section) {
        return new RTable(
                0,
                getNewTableNumber(),
                request.capacity(),
                false,
                section
        );
    }

    private TableInfoDto mapToTableInfoDto(RTable table) {
        return new TableInfoDto(
                table.getId(),
                table.getTableNumber(),
                table.getSection().getSectionName(),
                table.getCapacity()
        );
    }

    private long getNewTableNumber() {
        return this.tableRepository.count() + 1;
    }
}
