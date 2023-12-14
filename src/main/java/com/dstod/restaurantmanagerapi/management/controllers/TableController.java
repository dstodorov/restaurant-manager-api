package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateTableRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.TableInfoDto;
import com.dstod.restaurantmanagerapi.management.models.dtos.UpdateTableRequest;
import com.dstod.restaurantmanagerapi.management.services.TableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
@Tag(name = "Manage tables APIs")
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @Operation(summary = "Create new table")
    @PostMapping
    public ResponseEntity<SuccessResponse> createTable(@Valid @RequestBody CreateTableRequest request, UriComponentsBuilder uri) {

        SuccessResponse response = tableService.createTable(request);
        TableInfoDto savedTable = (TableInfoDto) response.savedObject();

        UriComponents uriComponents = uri.path("/tables/{id}").buildAndExpand(savedTable.id());
        return ResponseEntity.created(uriComponents.toUri()).body(response);
    }

    @Operation(summary = "Update table")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateTable(@Valid @RequestBody UpdateTableRequest request, @PathVariable long id) {
        return ResponseEntity.ok(tableService.updateTable(id, request));
    }

    @Operation(summary = "Get all tables info")
    @GetMapping
    public ResponseEntity<List<TableInfoDto>> getAllTables() {
        return ResponseEntity.ok(this.tableService.getAllTables());
    }

    @Operation(summary = "Get table info by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<TableInfoDto> getTableInfo(@PathVariable Long id) {
        return ResponseEntity.ok(this.tableService.getTableInfo(id));
    }
}
