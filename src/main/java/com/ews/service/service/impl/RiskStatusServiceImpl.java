package com.ews.service.service.impl;

import com.ews.service.entity.RiskStatus;
import com.ews.service.repository.spesification.RiskStatusSpesification;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.risk.GetListOptionStatusResponse;
import com.ews.service.service.RiskStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiskStatusServiceImpl implements RiskStatusService {

    private final RiskStatusSpesification riskStatusSpesification;

    @Override
    public PaginationResponse<GetListOptionStatusResponse> listOptionStatus(String name, int page) {
        try {

            Sort sortOrder = Sort.by(Sort.Direction.ASC, "orderNumber");
            Pageable pageable = PageRequest.of(page -1, 10, sortOrder);
            Page<RiskStatus> riskStatuses = riskStatusSpesification
                    .getPageFilterByStatusActive(name, pageable);

            Page<GetListOptionStatusResponse> responses = riskStatuses.map(riskStatus ->
                    new GetListOptionStatusResponse(riskStatus.getId(), riskStatus.getName()));

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responses.getContent(), responses.getNumber() + 1, responses.getSize(),
                    responses.getTotalElements(), responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }
}
