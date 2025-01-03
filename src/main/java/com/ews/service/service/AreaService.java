package com.ews.service.service;

import com.ews.service.request.area.CreateAreaRequest;
import com.ews.service.request.area.UpdateAreaRequest;
import com.ews.service.response.*;
import com.ews.service.response.area.CreateAreaResponse;
import com.ews.service.response.area.GetAreaResponse;
import com.ews.service.response.area.GetListSegmentForReportResponse;
import com.ews.service.response.area.UpdateAreaResponse;

import java.util.UUID;

public interface AreaService {

    PaginationResponse<GetAreaResponse> index(String name, int page, int limit, String sortBy, String sort);

    DataResponse<CreateAreaResponse> create(CreateAreaRequest request);

    DataResponse<UpdateAreaResponse> update(UUID id, UpdateAreaRequest request);

    DataResponse<Object> deleteById(UUID id);

    DataResponse<GetAreaResponse> getById(UUID id);

    PaginationResponse<GetAreaResponse> getListArea(String name, int page);

    PaginationResponse<GetListSegmentForReportResponse> getListSegmentForReport(String name, int emptyValue, int page);

}
