package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateTableRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.TableInfoDto;
import com.dstod.restaurantmanagerapi.management.models.dtos.UpdateTableRequest;
import com.dstod.restaurantmanagerapi.management.services.TableService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/tables")
public class TableController {
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createTable(@Valid @RequestBody CreateTableRequest request, UriComponentsBuilder uri) {

        SuccessResponse response = tableService.createTable(request);
        TableInfoDto savedTable = (TableInfoDto) response.savedObject();

        UriComponents uriComponents = uri.path("/tables/{id}").buildAndExpand(savedTable.id());
        return ResponseEntity.created(uriComponents.toUri()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateTable(@Valid @RequestBody UpdateTableRequest request, @PathVariable long id) {
        return ResponseEntity.ok(tableService.updateTable(id, request));
    }
}
