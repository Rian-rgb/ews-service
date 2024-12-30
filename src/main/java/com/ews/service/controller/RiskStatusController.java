package com.ews.service.controller;

import com.ews.service.response.PaginationResponse;
import com.ews.service.response.risk.GetListOptionStatusResponse;
import com.ews.service.service.RiskStatusService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master_data/risk")
@RequiredArgsConstructor
public class RiskStatusController {

    private final RiskStatusService riskStatusService;

    @GetMapping("/list-status")
    public ResponseEntity<PaginationResponse<GetListOptionStatusResponse>> listOptionStatus(
            @RequestParam(value = "term", required = false) String name,
            @RequestParam(defaultValue = "1") @Min(1) int page
    ) {
        return ResponseEntity.ok(riskStatusService.listOptionStatus(name, page));
    }
}
