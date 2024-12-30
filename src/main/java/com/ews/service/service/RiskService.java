package com.ews.service.service;

import com.ews.service.request.risk.CreateRiskRequest;
import com.ews.service.request.risk.UpdateRiskRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.risk.CreateRiskResponse;
import com.ews.service.response.risk.GetRiskResponse;
import com.ews.service.response.risk.UpdateRiskResponse;

import java.util.UUID;

public interface RiskService {

    PaginationResponse<GetRiskResponse> index(String name, int page, int limit, String sortBy, String sort);

    DataResponse<CreateRiskResponse> create(CreateRiskRequest request);

    DataResponse<UpdateRiskResponse> update(UUID id, UpdateRiskRequest request);

    DataResponse<GetRiskResponse> getById(UUID id);

    DataResponse<Object> deleteById(UUID id);

}
