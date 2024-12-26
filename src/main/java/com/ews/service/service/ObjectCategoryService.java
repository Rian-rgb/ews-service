package com.ews.service.service;

import com.ews.service.request.objectcategory.CreateObjectCategoryRequest;
import com.ews.service.request.objectcategory.UpdateObjectCategoryRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.objectcategory.CreateObjectCategoryResponse;
import com.ews.service.response.objectcategory.GetListObjectForReportResponse;
import com.ews.service.response.objectcategory.GetObjectCategoryResponse;
import com.ews.service.response.objectcategory.UpdateObjectCategoryResponse;

import java.util.UUID;

public interface ObjectCategoryService {

    PaginationResponse<GetObjectCategoryResponse> index(String name, int page, int limit, String sortBy, String sort);

    DataResponse<CreateObjectCategoryResponse> create(CreateObjectCategoryRequest request);

    DataResponse<UpdateObjectCategoryResponse> update(UUID id, UpdateObjectCategoryRequest request);

    DataResponse<GetObjectCategoryResponse> getById(UUID id);

    DataResponse<Object> deleteById(UUID id);

    PaginationResponse<GetObjectCategoryResponse> getListObjectCategory(String name, int page);

    PaginationResponse<GetListObjectForReportResponse> getListObjectForReport(String name, int emptyValue, int page,
                                                                              UUID segmentId);

}
