package com.ews.service.service.impl;

import com.ews.service.entity.DatabaseType;
import com.ews.service.entity.KeyProcesses;
import com.ews.service.entity.ObjectCategory;
import com.ews.service.exception.custom.ConflictException;
import com.ews.service.exception.custom.ConstraintValueException;
import com.ews.service.exception.custom.NotFoundException;
import com.ews.service.repository.DatabaseTypeRepository;
import com.ews.service.repository.spesification.DatabaseTypeSpesification;
import com.ews.service.request.databasetype.CreateDatabaseTypeRequest;
import com.ews.service.request.databasetype.UpdateDatabaseTypeRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.area.GetAreaResponse;
import com.ews.service.response.databasetype.CreateDatabaseTypeResponse;
import com.ews.service.response.databasetype.GetDatabaseTypeByIdResponse;
import com.ews.service.response.databasetype.GetDatabaseTypeResponse;
import com.ews.service.response.databasetype.UpdateDatabaseTypeResponse;
import com.ews.service.response.keyprocesses.CreateKeyProcessesResponse;
import com.ews.service.response.keyprocesses.GetKeyProcessesResponse;
import com.ews.service.response.keyprocesses.UpdateKeyProcessesResponse;
import com.ews.service.response.risk.GetRiskResponse;
import com.ews.service.service.DatabaseTypeService;
import com.ews.service.util.Base64Encryption;
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
public class DatabaseTypeServiceImpl implements DatabaseTypeService {

    private final DatabaseTypeRepository databaseTypeRepository;

    private final DatabaseTypeSpesification databaseTypeSpesification;

    @Override
    public PaginationResponse<GetDatabaseTypeResponse> index(String name, int page, int limit,
                                                             String sortBy, String sort) {
        try {

            String[] allowedOrder = {"createdAt"};
            Sort sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
            if ( Arrays.asList(allowedOrder).contains(sortBy) ) {
                sortOrder = sort.equalsIgnoreCase("ASC")
                        ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
            }

            Pageable pageable = PageRequest.of(page -1, limit, sortOrder);
            Page<DatabaseType> databaseTypes = databaseTypeSpesification
                    .getPageFilterByLikeName(name, sortBy, sort, pageable);

            Page<GetDatabaseTypeResponse> responses = databaseTypes.map(databaseType ->
                    new GetDatabaseTypeResponse(databaseType.getId(), databaseType.getName(),
                            databaseType.getConnectionName(), databaseType.getConnection(), databaseType.getDbHost(),
                            databaseType.getDbPort(), databaseType.getDbName(), databaseType.getDbUserName()));

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responses.getContent(), responses.getNumber() + 1,
                    responses.getSize(), responses.getTotalElements(),
                    responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<CreateDatabaseTypeResponse> create(CreateDatabaseTypeRequest request) {
        try {

            databaseTypeRepository.findByConnectionName(request.getConnectionName())
                    .ifPresent(data -> {
                        throw new ConstraintValueException("The connection name has already been taken.");
                    });

            DatabaseType databaseType = new DatabaseType();
            databaseType.setName(request.getName());
            databaseType.setConnection(request.getConnection());
            databaseType.setIsActive((byte) 1);
            databaseType.setDbHost(request.getDbHost());
            databaseType.setDbPort(request.getDbPort());
            databaseType.setDbName(request.getDbName());
            databaseType.setDbUserName(request.getDbUserName());
            databaseType.setDbPass(Base64Encryption.encrypt(request.getDbPass()));
            databaseType.setConnectionName(request.getConnectionName());
            databaseTypeRepository.save(databaseType);

            DatabaseType savedDatabaseType = databaseTypeRepository.save(databaseType);

            CreateDatabaseTypeResponse response = CreateDatabaseTypeResponse.builder()
                        .name(savedDatabaseType.getName())
                        .connectionName(savedDatabaseType.getConnectionName())
                        .connection(savedDatabaseType.getConnection())
                        .dbHost(savedDatabaseType.getDbHost())
                        .dbPort(savedDatabaseType.getDbPort())
                        .dbName(savedDatabaseType.getDbName())
                        .dbUserName(savedDatabaseType.getDbUserName())
                        .build();

            return new DataResponse<>(HttpStatus.CREATED.value(),
                    "Data konfigurasi database berhasil disimpan!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<UpdateDatabaseTypeResponse> update(UUID id, UpdateDatabaseTypeRequest request) {
        try {

            DatabaseType existingDatabaseType = databaseTypeRepository.findById(id)
                    .orElseThrow(() ->
                            new NotFoundException("Database tidak ditemukan dengan id: " + id));

            existingDatabaseType.setName(request.getName());
            existingDatabaseType.setConnectionName(request.getConnectionName());
            existingDatabaseType.setConnection(request.getConnection());
            existingDatabaseType.setDbHost(request.getDbHost());
            existingDatabaseType.setDbPort(request.getDbPort());
            existingDatabaseType.setDbName(request.getDbName());
            existingDatabaseType.setDbUserName(request.getDbUserName());
            existingDatabaseType.setDbPass(Base64Encryption.encrypt(request.getDbPass()));
            databaseTypeRepository.save(existingDatabaseType);

            UpdateDatabaseTypeResponse response = UpdateDatabaseTypeResponse.builder()
                    .name(existingDatabaseType.getName())
                    .connectionName(existingDatabaseType.getConnectionName())
                    .connection(existingDatabaseType.getConnection())
                    .dbHost(existingDatabaseType.getDbHost())
                    .dbPort(existingDatabaseType.getDbPort())
                    .dbName(existingDatabaseType.getDbName())
                    .dbUserName(existingDatabaseType.getDbUserName())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(),
                    "Data konfigurasi database berhasil diubah!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<GetDatabaseTypeByIdResponse> getById(UUID id) {
        try {

            DatabaseType existingDatabaseType = databaseTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Database tidak ditemukan dengan id: " + id));

            GetDatabaseTypeByIdResponse response = GetDatabaseTypeByIdResponse.builder()
                    .id(existingDatabaseType.getId())
                    .name(existingDatabaseType.getName())
                    .connectionName(existingDatabaseType.getConnectionName())
                    .connection(existingDatabaseType.getConnection())
                    .dbHost(existingDatabaseType.getDbHost())
                    .dbPort(existingDatabaseType.getDbPort())
                    .dbName(existingDatabaseType.getDbName())
                    .dbUserName(existingDatabaseType.getDbUserName())
                    .dbPass(existingDatabaseType.getDbPass())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Success", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<Object> deleteById(UUID id) {
        try {

            databaseTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Database tidak ditemukan dengan id: " + id));

            databaseTypeRepository.deleteById(id);

            return new DataResponse<>(HttpStatus.OK.value(),
                    "Data konfigurasi database berhasil dihapus!", null);

        } catch (Exception e) {
            throw new RuntimeException("Data konfigurasi database gagal dihapus!");
        }
    }

    @Override
    public DataResponse<Object> checkConnection(UUID id, String dbPass) {
        return null;
    }
}
