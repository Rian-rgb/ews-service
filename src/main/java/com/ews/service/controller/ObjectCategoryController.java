package com.ews.service.controller;

import com.ews.service.request.objectcategory.CreateObjectCategoryRequest;
import com.ews.service.request.objectcategory.UpdateObjectCategoryRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.objectcategory.CreateObjectCategoryResponse;
import com.ews.service.response.objectcategory.GetListObjectForReportResponse;
import com.ews.service.response.objectcategory.GetObjectCategoryResponse;
import com.ews.service.response.objectcategory.UpdateObjectCategoryResponse;
import com.ews.service.service.ObjectCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/master_data/object_category")
@RequiredArgsConstructor
public class ObjectCategoryController {

    private final ObjectCategoryService objectCategoryService;

    @GetMapping
    public ResponseEntity<PaginationResponse<GetObjectCategoryResponse>> index(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sort,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(objectCategoryService.index(name, page, limit, sortBy, sort));
    }

    @PostMapping("/store")
    public ResponseEntity<DataResponse<CreateObjectCategoryResponse>> create(@RequestBody @Valid CreateObjectCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(objectCategoryService.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DataResponse<UpdateObjectCategoryResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateObjectCategoryRequest request) {
        return ResponseEntity.ok(objectCategoryService.update(id, request));
    }

    @DeleteMapping("/destroy/{id}")
    public ResponseEntity<DataResponse<Object>> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(objectCategoryService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<GetObjectCategoryResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(objectCategoryService.getById(id));
    }

    @GetMapping("/area/list")
    public ResponseEntity<PaginationResponse<GetObjectCategoryResponse>> getListArea(
            @RequestParam(value = "term", required = false) String name,
            @RequestParam(defaultValue = "1") @Min(1) int page
    ) {
        return ResponseEntity.ok(objectCategoryService.getListObjectCategory(name, page));
    }

    @GetMapping("/list-object_category")
    public ResponseEntity<PaginationResponse<GetListObjectForReportResponse>> getListSegmentForReport(
            @RequestParam(value = "term", required = false) String name,
            @RequestParam(defaultValue = "0") @Min(0) int emptyValue,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(value = "segmentId", required = false) UUID segmentId
    ) {
        return ResponseEntity.ok(objectCategoryService.getListObjectForReport(name, emptyValue, page, segmentId));
    }
}
