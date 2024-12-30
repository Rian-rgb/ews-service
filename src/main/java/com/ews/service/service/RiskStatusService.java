package com.ews.service.service;

import com.ews.service.response.PaginationResponse;
import com.ews.service.response.risk.GetListOptionStatusResponse;

public interface RiskStatusService {

    PaginationResponse<GetListOptionStatusResponse> listOptionStatus(String name, int page);
}
