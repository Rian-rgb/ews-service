package com.ews.service.service;

import com.ews.service.request.keyprocesses.CreateKeyProcessesRequest;
import com.ews.service.request.keyprocesses.UpdateKeyProcessesRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.keyprocesses.CreateKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetListKeyProcessesResponse;
import com.ews.service.response.keyprocesses.UpdateKeyProcessesResponse;

import java.util.UUID;

public interface KeyProcessesService {

    PaginationResponse<GetKeyProcessesResponse> index(String name, int page, int limit, String sortBy, String sort);

    DataResponse<CreateKeyProcessesResponse> create(CreateKeyProcessesRequest request);

    DataResponse<UpdateKeyProcessesResponse> update(UUID id, UpdateKeyProcessesRequest request);

    DataResponse<GetKeyProcessesResponse> getById(UUID id);

    DataResponse<Object> deleteById(UUID id);

    PaginationResponse<GetListKeyProcessesResponse> getListKeyProcess(String name, int page);

}
