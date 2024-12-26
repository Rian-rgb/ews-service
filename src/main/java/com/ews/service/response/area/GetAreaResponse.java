package com.ews.service.response.area;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAreaResponse {

    private UUID id;

    private String name;

    private Byte isActive;

}
