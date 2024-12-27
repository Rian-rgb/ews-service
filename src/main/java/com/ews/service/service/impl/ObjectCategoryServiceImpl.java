package com.ews.service.service.impl;

import com.ews.service.entity.Area;
import com.ews.service.entity.ObjectCategory;
import com.ews.service.exception.custom.ConstraintValueException;
import com.ews.service.exception.custom.NotFoundException;
import com.ews.service.repository.AreaRepository;
import com.ews.service.repository.ObjectCategoryRepository;
import com.ews.service.repository.spesification.ObjectCategorySpesification;
import com.ews.service.request.objectcategory.CreateObjectCategoryRequest;
import com.ews.service.request.objectcategory.UpdateObjectCategoryRequest;
import com.ews.service.response.DataResponse;
import com.ews.service.response.PaginationResponse;
import com.ews.service.response.objectcategory.CreateObjectCategoryResponse;
import com.ews.service.response.objectcategory.GetListObjectForReportResponse;
import com.ews.service.response.objectcategory.GetObjectCategoryResponse;
import com.ews.service.response.objectcategory.UpdateObjectCategoryResponse;
import com.ews.service.service.ObjectCategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjectCategoryServiceImpl implements ObjectCategoryService {

    private final ObjectCategoryRepository objectCategoryRepository;

    private final AreaRepository areaRepository;

    private final ObjectCategorySpesification objectCategorySpesification;

    @Override
    public PaginationResponse<GetObjectCategoryResponse> index(String name, int page, int limit,
                                                               String sortBy, String sort) {
        try {

            String[] allowedOrder = {"createdAt"};
            Sort sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
            if ( Arrays.asList(allowedOrder).contains(sortBy) ) {
                sortOrder = sort.equalsIgnoreCase("ASC")
                        ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
            }

            Pageable pageable = PageRequest.of(page -1, limit, sortOrder);
            Page<GetObjectCategoryResponse> responses = objectCategorySpesification
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
    public DataResponse<CreateObjectCategoryResponse> create(CreateObjectCategoryRequest request) {
        try {

            Area existingArea = areaRepository.findById(request.getAreaId())
                    .orElseThrow(() -> new NotFoundException("Area tidak ditemukan dengan id: " + request.getAreaId()));

            ObjectCategory objectCategory = new ObjectCategory();
            objectCategory.setName(request.getName());
            objectCategory.setIsActive((byte) 1);
            objectCategory.setArea(existingArea);
            objectCategoryRepository.save(objectCategory);

            CreateObjectCategoryResponse response = CreateObjectCategoryResponse.builder()
                    .name(objectCategory.getName())
                    .isActive(objectCategory.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.CREATED.value(), "Data kategori objek berhasil disimpan!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<UpdateObjectCategoryResponse> update(UUID id, UpdateObjectCategoryRequest request) {
        try {

            ObjectCategory existingObjectCategory = objectCategoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Objek Kategori tidak ditemukan dengan id: " + id));

            Area existingArea = areaRepository.findById(request.getAreaId())
                    .orElseThrow(() -> new NotFoundException("Area tidak ditemukan dengan id: " + request.getAreaId()));

            existingObjectCategory.setName(request.getName());
            existingObjectCategory.setIsActive(request.getIsActive());
            existingObjectCategory.setArea(existingArea);
            objectCategoryRepository.save(existingObjectCategory);

            UpdateObjectCategoryResponse response = UpdateObjectCategoryResponse.builder()
                    .name(existingArea.getName())
                    .isActive(existingArea.getIsActive())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Data kategori objek berhasil diubah!", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<GetObjectCategoryResponse> getById(UUID id) {
        try {

            ObjectCategory existingObjectCategory = objectCategoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Objek Kategori tidak ditemukan dengan id: " + id));

            GetObjectCategoryResponse response = GetObjectCategoryResponse.builder()
                    .id(existingObjectCategory.getId())
                    .name(existingObjectCategory.getName())
                    .isActive(existingObjectCategory.getIsActive())
                    .areaName(existingObjectCategory.getArea().getName())
                    .build();

            return new DataResponse<>(HttpStatus.OK.value(), "Success", response);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public DataResponse<Object> deleteById(UUID id) {
        try {

            objectCategoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Object Kategori tidak ditemukan dengan id: " + id));

            objectCategoryRepository.deleteById(id);

            return new DataResponse<>(HttpStatus.OK.value(), "Data kategori objek berhasil dihapus!", null);

        } catch (Exception e) {
            if (e instanceof ConstraintViolationException){
                throw new ConstraintValueException(
                        "Data Kategori Objek ini tidak bisa dihapus karena digunakan " +
                        "oleh Risk Event yang memiliki issue");
            }
            throw e;
        }
    }

    @Override
    public PaginationResponse<GetObjectCategoryResponse> getListObjectCategory(String name, int page) {
        try {

            Sort sortOrder = Sort.by(Sort.Direction.ASC, "name");
            Pageable pageable = PageRequest.of(page -1, 10, sortOrder);
            Page<GetObjectCategoryResponse> responses = objectCategorySpesification
                    .getPageFilterByLikeNameAndStatusActive(name, "name", "asc", pageable);

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responses.getContent(), responses.getNumber() + 1, responses.getSize(), responses.getTotalElements(),
                    responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PaginationResponse<GetListObjectForReportResponse> getListObjectForReport(String name, int emptyValue,
                                                                                int page, UUID segmentId) {
        try {

            Sort sortOrder = Sort.by(Sort.Direction.ASC, "createdAt");
            Pageable pageable = PageRequest.of(page - 1, 10, sortOrder);
            Page<GetListObjectForReportResponse> responses = objectCategorySpesification
                    .getPageFilterByLikeNameAndAreaIdAndStatusActive(name, "createdAt", "asc",
                            pageable, segmentId);

            List<GetListObjectForReportResponse> responseList = new ArrayList<>(responses.getContent());

            if (name == null && page == 1) {

                responseList.add(new GetListObjectForReportResponse("all", "Semua"));

                if (emptyValue == 1) {
                    responseList.add(new GetListObjectForReportResponse("", "Tidak Dipilih"));
                }

            }

            return new PaginationResponse<>(HttpStatus.OK.value(), "Success",
                    responseList, responses.getNumber() + 1, responses.getSize(), responses.getTotalElements(),
                    responses.getTotalPages(), responses.isLast());

        } catch (Exception e) {
            throw e;
        }
    }
}
