package com.ews.service.controller;

import com.ews.service.request.keyprocesses.CreateKeyProcessesRequest;
import com.ews.service.request.keyprocesses.UpdateKeyProcessesRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.keyprocesses.CreateKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetListKeyProcessesResponse;
import com.ews.service.response.keyprocesses.UpdateKeyProcessesResponse;
import com.ews.service.service.KeyProcessesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/master_data/key_process")
@RequiredArgsConstructor
public class KeyProcessesController {

    private final KeyProcessesService keyProcessesService;

    @GetMapping
    public ResponseEntity<PaginationResponse<GetKeyProcessesResponse>> index(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sort,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(keyProcessesService.index(name, page, limit, sortBy, sort));
    }

    @PostMapping("/store")
    public ResponseEntity<DataResponse<CreateKeyProcessesResponse>> create(
            @RequestBody @Valid CreateKeyProcessesRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(keyProcessesService.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DataResponse<UpdateKeyProcessesResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateKeyProcessesRequest request) {
        return ResponseEntity.ok(keyProcessesService.update(id, request));
    }

    @DeleteMapping("/destroy/{id}")
    public ResponseEntity<DataResponse<Object>> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(keyProcessesService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<GetKeyProcessesResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(keyProcessesService.getById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<PaginationResponse<GetListKeyProcessesResponse>> getListKeyProcess(
            @RequestParam(value = "term", required = false) String name,
            @RequestParam(defaultValue = "1") @Min(1) int page
    ) {
        return ResponseEntity.ok(keyProcessesService.getListKeyProcess(name, page));
    }
}
