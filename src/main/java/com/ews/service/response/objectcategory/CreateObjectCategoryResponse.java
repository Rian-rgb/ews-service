package com.ews.service.response.objectcategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateObjectCategoryResponse {

    private String name;

    private Byte isActive;

}
