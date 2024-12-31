package com.ews.service.controller;

import com.ews.service.request.databasetype.CreateDatabaseTypeRequest;
import com.ews.service.request.databasetype.UpdateDatabaseTypeRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.databasetype.CreateDatabaseTypeResponse;
import com.ews.service.response.databasetype.GetDatabaseTypeByIdResponse;
import com.ews.service.response.databasetype.GetDatabaseTypeResponse;
import com.ews.service.response.databasetype.UpdateDatabaseTypeResponse;
import com.ews.service.service.DatabaseTypeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/configuration_database")
@RequiredArgsConstructor
public class DatabaseTypeController {

    private final DatabaseTypeService databaseTypeService;

    @GetMapping
    public ResponseEntity<PaginationResponse<GetDatabaseTypeResponse>> index(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sort,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(databaseTypeService.index(name, page, limit, sortBy, sort));
    }

    @PostMapping("/store")
    public ResponseEntity<DataResponse<CreateDatabaseTypeResponse>> create(
            @RequestBody @Valid CreateDatabaseTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(databaseTypeService.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DataResponse<UpdateDatabaseTypeResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateDatabaseTypeRequest request) {
        return ResponseEntity.ok(databaseTypeService.update(id, request));
    }

    @DeleteMapping("/destroy/{id}")
    public ResponseEntity<DataResponse<Object>> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(databaseTypeService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<GetDatabaseTypeByIdResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(databaseTypeService.getById(id));
    }
}
