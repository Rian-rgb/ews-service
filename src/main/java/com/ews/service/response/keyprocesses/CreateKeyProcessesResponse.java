package com.ews.service.response.keyprocesses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateKeyProcessesResponse {

    private String name;

    private Byte isActive;

}
