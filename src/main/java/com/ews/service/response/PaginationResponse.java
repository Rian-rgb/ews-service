package com.ews.service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaginationResponse<T> {

    private int statusCode;
    private String message;
    private List<T> datas;
    private int currentPage;
    private int pageLimit;
    private long totalElements;
    private int totalPages;
    private boolean last;

}
