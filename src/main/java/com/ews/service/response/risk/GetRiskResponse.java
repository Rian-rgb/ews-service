package com.ews.service.response.risk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetRiskResponse {

    private UUID id;

    private String codeName;

    private String description;

    private Byte isActive;

    private String riskStatusName;

    private String keyProcessName;

    private String objectCategoryName;

    private String areaName;

}
