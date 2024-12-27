package com.ews.service.response.keyprocesses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetKeyProcessesResponse {

    private UUID id;

    private String name;

    private Byte isActive;

    private String areaName;

    private String objectCategoryName;

}
