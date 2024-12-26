package com.ews.service.response.objectcategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetListObjectForReportResponse {

    private String id;

    private String text;

}
