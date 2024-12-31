package com.ews.service.service;

import com.ews.service.request.databasetype.CreateDatabaseTypeRequest;
import com.ews.service.request.databasetype.UpdateDatabaseTypeRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.databasetype.CreateDatabaseTypeResponse;
import com.ews.service.response.databasetype.GetDatabaseTypeByIdResponse;
import com.ews.service.response.databasetype.GetDatabaseTypeResponse;
import com.ews.service.response.databasetype.UpdateDatabaseTypeResponse;
import com.ews.service.response.risk.GetRiskResponse;

import java.util.UUID;

public interface DatabaseTypeService {

    PaginationResponse<GetDatabaseTypeResponse> index(String name, int page, int limit, String sortBy, String sort);

    DataResponse<CreateDatabaseTypeResponse> create(CreateDatabaseTypeRequest request);

    DataResponse<UpdateDatabaseTypeResponse> update(UUID id, UpdateDatabaseTypeRequest request);

    DataResponse<GetDatabaseTypeByIdResponse> getById(UUID id);

    DataResponse<Object> deleteById(UUID id);

    DataResponse<Object> checkConnection(UUID id, String dbPass);

}
