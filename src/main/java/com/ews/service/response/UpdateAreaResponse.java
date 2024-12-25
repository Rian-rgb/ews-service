package com.ews.service.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateAreaResponse {

    private String name;

    private Byte isActive;
}
