package com.ews.service.service;

import com.ews.service.model.Area;
import com.ews.service.request.area.CreateAreaRequest;
import com.ews.service.request.area.UpdateAreaRequest;
import com.ews.service.response.*;

import java.util.UUID;

public interface AreaService {

    PaginationResponse<Area> findPage(String name, int page, int size, String sortBy, String sort);

    DataResponse<CreateAreaResponse> create(CreateAreaRequest request);

    DataResponse<UpdateAreaResponse> update(UUID id, UpdateAreaRequest request);

    DataResponse<Object> deleteById(UUID id);

}
