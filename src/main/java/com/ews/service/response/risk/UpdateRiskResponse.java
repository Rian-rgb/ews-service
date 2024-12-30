package com.ews.service.response.risk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRiskResponse {

    private String codeName;

    private String description;

    private Byte isActive;

}
