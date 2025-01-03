package com.ews.service.controller;

import com.ews.service.request.area.CreateAreaRequest;
import com.ews.service.request.area.UpdateAreaRequest;
import com.ews.service.response.*;
import com.ews.service.response.area.CreateAreaResponse;
import com.ews.service.response.area.GetAreaResponse;
import com.ews.service.response.area.GetListSegmentForReportResponse;
import com.ews.service.response.area.UpdateAreaResponse;
import com.ews.service.service.AreaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/master_data/area")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<PaginationResponse<GetAreaResponse>> index(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sort,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(areaService.index(name, page, limit, sortBy, sort));
    }

    @PostMapping("/store")
    public ResponseEntity<DataResponse<CreateAreaResponse>> create(@RequestBody @Valid CreateAreaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(areaService.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DataResponse<UpdateAreaResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateAreaRequest request) {
        return ResponseEntity.ok(areaService.update(id, request));
    }

    @DeleteMapping("/destroy/{id}")
    public ResponseEntity<DataResponse<Object>> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(areaService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<GetAreaResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(areaService.getById(id));
    }

    @GetMapping("/area/list")
    public ResponseEntity<PaginationResponse<GetAreaResponse>> getListArea(
            @RequestParam(value = "term", required = false) String name,
            @RequestParam(defaultValue = "1") @Min(1) int page
    ) {
        return ResponseEntity.ok(areaService.getListArea(name, page));
    }

    @GetMapping("/list-segment")
    public ResponseEntity<PaginationResponse<GetListSegmentForReportResponse>> getListSegmentForReport(
            @RequestParam(value = "term", required = false) String name,
            @RequestParam(defaultValue = "0") @Min(0) int emptyValue,
            @RequestParam(defaultValue = "1") @Min(1) int page
    ) {
        return ResponseEntity.ok(areaService.getListSegmentForReport(name, emptyValue, page));
    }
}
