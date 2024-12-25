package com.ews.service.service.impl;

import com.ews.service.exception.custom.ConflictException;
import com.ews.service.exception.custom.ConstraintValueException;
import com.ews.service.exception.custom.NotFoundException;
import com.ews.service.model.Area;
import com.ews.service.repository.AreaRepository;
import com.ews.service.request.area.CreateAreaRequest;
import com.ews.service.request.area.UpdateAreaRequest;
import com.ews.service.response.*;
import com.ews.service.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.hibernate.QueryException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public PaginationResponse<Area> findPage(String name, int page, int size, String sortBy, String sort) {
        try {

            String[] allowedOrder = {"createdAt"};
            Sort sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
            if ( Arrays.asList(allowedOrder).contains(sortBy) ) {
                sortOrder = sort.equalsIgnoreCase("ASC")
                        ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sort);
            }

            Pageable pageable = PageRequest.of(page -1, size, sortOrder);
            Page<Area> datas = areaRepository.findPage(name, pageable);

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    datas.getContent(), datas.getNumber(), datas.getSize(), datas.getTotalElements(),
                    datas.getTotalPages(), datas.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<CreateAreaResponse> create(CreateAreaRequest request) {
        try {

            Area area = new Area();
            area.setName(request.getName());
            area.setIsActive((byte) 1);
            areaRepository.save(area);

            CreateAreaResponse response = CreateAreaResponse.builder()
                    .name(area.getName())
                    .isActive(area.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.CREATED.value(), "Data bidang berhasil disimpan!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<UpdateAreaResponse> update(UUID id, UpdateAreaRequest request) {
        try {

            Area existingArea = areaRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Area tidak ditemukan dengan id: " + id));

            existingArea.setName(request.getName());
            existingArea.setIsActive(request.getIsActive());
            areaRepository.save(existingArea);

            UpdateAreaResponse response = UpdateAreaResponse.builder()
                    .name(existingArea.getName())
                    .isActive(existingArea.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Data bidang berhasil diubah!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<Object> deleteById(UUID id) {
        try {

            areaRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Area tidak ditemukan dengan id: " + id));

            areaRepository.deleteById(id);

            return new DataResponse<>(HttpStatus.OK.value(), "Data bidang berhasil dihapus!", null);

        } catch (Exception e) {
            if (e instanceof ConstraintViolationException){
                Area area = areaRepository.findById(id).orElse(null);
                throw new ConstraintValueException("Data " + area.getName() + " ini tidak bisa dihapus karena digunakan " +
                        "oleh Risk Event yang memiliki issue");
            }
            throw e;
        }
    }
}
