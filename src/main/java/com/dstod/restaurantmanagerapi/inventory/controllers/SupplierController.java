package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDTO;
import com.dstod.restaurantmanagerapi.inventory.services.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.dstod.restaurantmanagerapi.inventory.models.ErrorMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierDTO> addSupplier(@RequestBody @Valid SupplierDTO supplierDTO, UriComponentsBuilder uriComponentsBuilder) {
        Long supplierId = this.supplierService.createSupplier(supplierDTO);

        if (supplierId == -1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/suppliers/{id}").build(supplierId)).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        Optional<SupplierDTO> supplier = this.supplierService.getSupplier(id);

        return supplier.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplierInfo(@PathVariable Long id, @RequestBody @Valid SupplierDTO supplierDTO) {
        SupplierDTO supplier = this.supplierService.updateSupplier(id, supplierDTO);

        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return this.supplierService.getAllSuppliers().map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleValidationExceptions(BindingResult bindingResult) {

        List<String> errors = new ArrayList<>();

        bindingResult.getAllErrors().forEach(e -> errors.add(new ErrorMessage(
                ((FieldError) e).getField(),
                e.getDefaultMessage()
        ).toString()));

        String responseBody = String.format("{\"errors\" : [%s]}", String.join(", ", errors));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(responseBody);
    }
}
