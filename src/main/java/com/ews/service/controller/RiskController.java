package com.ews.service.controller;

import com.ews.service.request.risk.CreateRiskRequest;
import com.ews.service.request.risk.UpdateRiskRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.risk.CreateRiskResponse;
import com.ews.service.response.risk.GetRiskResponse;
import com.ews.service.response.risk.UpdateRiskResponse;
import com.ews.service.service.RiskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/master_data/risk")
@RequiredArgsConstructor
public class RiskController {

    private final RiskService riskService;

    @GetMapping
    public ResponseEntity<PaginationResponse<GetRiskResponse>> index(
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "10") @Min(1) int limit,
            @RequestParam(defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sort,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(riskService.index(name, page, limit, sortBy, sort));
    }

    @PostMapping("/store")
    public ResponseEntity<DataResponse<CreateRiskResponse>> create(
            @RequestBody @Valid CreateRiskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(riskService.create(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DataResponse<UpdateRiskResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateRiskRequest request) {
        return ResponseEntity.ok(riskService.update(id, request));
    }

    @DeleteMapping("/destroy/{id}")
    public ResponseEntity<DataResponse<Object>> deleteById(@PathVariable UUID id) {
        return ResponseEntity.ok(riskService.deleteById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<GetRiskResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(riskService.getById(id));
    }
}
