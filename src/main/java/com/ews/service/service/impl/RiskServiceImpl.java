package com.ews.service.service.impl;

import com.ews.service.entity.KeyProcesses;
import com.ews.service.entity.Risk;
import com.ews.service.entity.RiskStatus;
import com.ews.service.exception.custom.ConstraintValueException;
import com.ews.service.exception.custom.NotFoundException;
import com.ews.service.repository.KeyProcessesRepository;
import com.ews.service.repository.RiskRepository;
import com.ews.service.repository.RiskStatusRepository;
import com.ews.service.repository.spesification.RiskSpesification;
import com.ews.service.repository.spesification.RiskStatusSpesification;
import com.ews.service.request.risk.CreateRiskRequest;
import com.ews.service.request.risk.UpdateRiskRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.risk.CreateRiskResponse;
import com.ews.service.response.risk.GetRiskResponse;
import com.ews.service.response.risk.UpdateRiskResponse;
import com.ews.service.service.RiskService;
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
public class RiskServiceImpl implements RiskService {

    private final RiskRepository riskRepository;

    private final KeyProcessesRepository keyProcessesRepository;

    private final RiskStatusRepository riskStatusRepository;

    private final RiskSpesification riskSpesification;

    private final RiskStatusSpesification riskStatusSpesification;

    @Override
    public PaginationResponse<GetRiskResponse> index(String name, int page, int limit, String sortBy, String sort) {
        try {

            String[] allowedOrder = {"createdAt"};
            Sort sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
            if ( Arrays.asList(allowedOrder).contains(sortBy) ) {
                sortOrder = sort.equalsIgnoreCase("ASC")
                        ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
            }

            Pageable pageable = PageRequest.of(page -1, limit, sortOrder);
            Page<Risk> risks = riskSpesification
                    .getPageFilterByLikeName(name, sortBy, sort, pageable);

            Page<GetRiskResponse> responses = risks.map(risk ->
                    new GetRiskResponse(risk.getId(), risk.getCodeName(), risk.getDescription(), risk.getIsActive(),
                            risk.getRiskStatus().getName(), risk.getKeyProcess().getName(),
                            risk.getKeyProcess().getObjectCategory().getName(),
                            risk.getKeyProcess().getObjectCategory().getArea().getName()));

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responses.getContent(), responses.getNumber() + 1,
                    responses.getSize(), responses.getTotalElements(),
                    responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<CreateRiskResponse> create(CreateRiskRequest request) {
        try {

            KeyProcesses existingKeyProcesses = keyProcessesRepository.findById(request.getKeyProcessId())
                    .orElseThrow(() ->
                            new NotFoundException("Key Process tidak ditemukan dengan id: " +
                                    request.getKeyProcessId()));

            RiskStatus existingRiskStatus = riskStatusRepository.findById(request.getRiskStatusId())
                    .orElseThrow(() ->
                            new NotFoundException("Risk Status tidak ditemukan dengan id: " +
                                    request.getRiskStatusId()));

            Risk risk = new Risk();
            risk.setCodeName(request.getCodeName());
            risk.setDescription(request.getDescription());
            risk.setIsActive((byte) 1);
            risk.setKeyProcess(existingKeyProcesses);
            risk.setRiskStatus(existingRiskStatus);
            riskRepository.save(risk);

            CreateRiskResponse response = CreateRiskResponse.builder()
                    .codeName(risk.getCodeName())
                    .description(risk.getDescription())
                    .isActive(risk.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.CREATED.value(), "Data risiko berhasil disimpan!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<UpdateRiskResponse> update(UUID id, UpdateRiskRequest request) {
        try {

            Risk existingRisk = riskRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Risk tidak ditemukan dengan id: " + id));

            KeyProcesses existingKeyProcesses = keyProcessesRepository.findById(request.getKeyProcessId())
                    .orElseThrow(() ->
                            new NotFoundException("Key Process tidak ditemukan dengan id: " +
                                    request.getKeyProcessId()));

            RiskStatus existingRiskStatus = riskStatusRepository.findById(request.getRiskStatusId())
                    .orElseThrow(() ->
                            new NotFoundException("Risk Status tidak ditemukan dengan id: " +
                                    request.getRiskStatusId()));

            existingRisk.setCodeName(request.getCodeName());
            existingRisk.setDescription(request.getDescription());
            existingRisk.setKeyProcess(existingKeyProcesses);
            existingRisk.setRiskStatus(existingRiskStatus);
            existingRisk.setIsActive(request.getIsActive());
            riskRepository.save(existingRisk);

            UpdateRiskResponse response = UpdateRiskResponse.builder()
                    .codeName(existingRisk.getCodeName())
                    .description(existingRisk.getDescription())
                    .isActive(existingRisk.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Data risiko berhasil diubah!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<GetRiskResponse> getById(UUID id) {
        try {

            Risk existingRisk = riskRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Risk tidak ditemukan dengan id: " + id));

            GetRiskResponse response = GetRiskResponse.builder()
                    .id(existingRisk.getId())
                    .codeName(existingRisk.getCodeName())
                    .description(existingRisk.getDescription())
                    .isActive(existingRisk.getIsActive())
                    .riskStatusName(existingRisk.getRiskStatus().getName())
                    .keyProcessName(existingRisk.getKeyProcess().getName())
                    .objectCategoryName(existingRisk.getKeyProcess().getObjectCategory().getName())
                    .areaName(existingRisk.getKeyProcess().getObjectCategory().getArea().getName())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Success", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<Object> deleteById(UUID id) {
        try {

            riskRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Risk tidak ditemukan dengan id: " + id));

            riskRepository.deleteById(id);

            return new DataResponse<>(HttpStatus.OK.value(), "Data risiko berhasil dihapus!", null);

        } catch (Exception e) {
            if (e instanceof ConstraintViolationException){
                throw new ConstraintValueException(
                        "Data Risiko ini tidak bisa dihapus karena digunakan " +
                                "oleh Risk Event yang memiliki issue");
            }
            throw e;
        }
    }
}
