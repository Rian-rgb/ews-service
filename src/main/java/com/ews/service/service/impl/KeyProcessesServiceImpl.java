package com.ews.service.service.impl;

import com.ews.service.entity.KeyProcesses;
import com.ews.service.entity.ObjectCategory;
import com.ews.service.exception.custom.ConstraintValueException;
import com.ews.service.exception.custom.NotFoundException;
import com.ews.service.repository.KeyProcessesRepository;
import com.ews.service.repository.ObjectCategoryRepository;
import com.ews.service.repository.spesification.KeyProcessesSpesification;
import com.ews.service.request.keyprocesses.CreateKeyProcessesRequest;
import com.ews.service.request.keyprocesses.UpdateKeyProcessesRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.keyprocesses.CreateKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetListKeyProcessesResponse;
import com.ews.service.response.keyprocesses.UpdateKeyProcessesResponse;
import com.ews.service.service.KeyProcessesService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeyProcessesServiceImpl implements KeyProcessesService {

    private final KeyProcessesRepository keyProcessesRepository;

    private final ObjectCategoryRepository objectCategoryRepository;

    private final KeyProcessesSpesification keyProcessesSpesification;

    @Override
    public PaginationResponse<GetKeyProcessesResponse> index(String name, int page, int limit,
                                                             String sortBy, String sort) {
        try {

            String[] allowedOrder = {"createdAt"};
            Sort sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
            if ( Arrays.asList(allowedOrder).contains(sortBy) ) {
                sortOrder = sort.equalsIgnoreCase("ASC")
                        ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
            }

            Pageable pageable = PageRequest.of(page -1, limit, sortOrder);
            Page<GetKeyProcessesResponse> responses = keyProcessesSpesification
                    .getPageFilterByLikeName(name, sortBy, sort, pageable);

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responses.getContent(), responses.getNumber() + 1,
                    responses.getSize(), responses.getTotalElements(),
                    responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<CreateKeyProcessesResponse> create(CreateKeyProcessesRequest request) {
        try {

            ObjectCategory existingObjectCategory = objectCategoryRepository.findById(request.getObjectCategoryId())
                    .orElseThrow(() ->
                            new NotFoundException("Objek Kategori tidak ditemukan dengan id: " +
                                    request.getObjectCategoryId()));

            KeyProcesses keyProcesses = new KeyProcesses();
            keyProcesses.setName(request.getName());
            keyProcesses.setIsActive((byte) 1);
            keyProcesses.setObjectCategory(existingObjectCategory);
            keyProcessesRepository.save(keyProcesses);

            CreateKeyProcessesResponse response = CreateKeyProcessesResponse.builder()
                    .name(keyProcesses.getName())
                    .isActive(keyProcesses.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.CREATED.value(),
                    "Data kunci proses berhasil disimpan!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<UpdateKeyProcessesResponse> update(UUID id, UpdateKeyProcessesRequest request) {
        try {

            KeyProcesses existingKeyProcesses = keyProcessesRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Key Processes tidak ditemukan dengan id: " + id));

            ObjectCategory existingObjectCategory = objectCategoryRepository.findById(request.getObjectCategoryId())
                    .orElseThrow(() ->
                            new NotFoundException("Objek Kategori tidak ditemukan dengan id: " +
                                    request.getObjectCategoryId()));

            existingKeyProcesses.setName(request.getName());
            existingKeyProcesses.setIsActive(request.getIsActive());
            existingKeyProcesses.setObjectCategory(existingObjectCategory);
            keyProcessesRepository.save(existingKeyProcesses);

            UpdateKeyProcessesResponse response = UpdateKeyProcessesResponse.builder()
                    .name(existingKeyProcesses.getName())
                    .isActive(existingKeyProcesses.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Data kunci proses berhasil diubah!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<GetKeyProcessesResponse> getById(UUID id) {
        try {

            KeyProcesses existingKeyProcesses = keyProcessesRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Key Processes tidak ditemukan dengan id: " + id));

            GetKeyProcessesResponse response = GetKeyProcessesResponse.builder()
                    .id(existingKeyProcesses.getId())
                    .name(existingKeyProcesses.getName())
                    .isActive(existingKeyProcesses.getIsActive())
                    .areaName(existingKeyProcesses.getObjectCategory().getArea().getName())
                    .objectCategoryName(existingKeyProcesses.getObjectCategory().getName())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Success", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<Object> deleteById(UUID id) {
        try {

            keyProcessesRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Key Processes tidak ditemukan dengan id: " + id));

            keyProcessesRepository.deleteById(id);

            return new DataResponse<>(HttpStatus.OK.value(), "Data kunci proses berhasil dihapus!", null);

        } catch (Exception e) {
            if (e instanceof ConstraintViolationException){
                throw new ConstraintValueException(
                        "Data Kunci Proses ini tidak bisa dihapus karena digunakan " +
                                "oleh Risk Event yang memiliki issue");
            }
            throw e;
        }
    }

    @Override
    public PaginationResponse<GetListKeyProcessesResponse> getListKeyProcess(String name, int page) {
        try {

            Sort sortOrder = Sort.by(Sort.Direction.ASC, "name");
            Pageable pageable = PageRequest.of(page -1, 10, sortOrder);
            Page<GetListKeyProcessesResponse> responses = keyProcessesSpesification
                    .getActiveKeyProcesses(name, pageable);

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responses.getContent(), responses.getNumber() + 1, responses.getSize(), responses.getTotalElements(),
                    responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }
}
