package com.ews.service.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GetAreaResponse {

    private UUID id;
    private String name;
}
