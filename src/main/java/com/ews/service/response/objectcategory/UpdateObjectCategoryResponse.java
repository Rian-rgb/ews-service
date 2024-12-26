package com.ews.service.response.objectcategory;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateObjectCategoryResponse {

    private String name;

    private Byte isActive;

}
