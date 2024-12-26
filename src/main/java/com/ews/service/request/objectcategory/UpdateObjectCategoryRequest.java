package com.ews.service.request.objectcategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateObjectCategoryRequest {

    @NotBlank(message = "Nama Kategori Objek harus diisi")
    @Size(min = 1, max = 100, message = "Nama Kategori harus antara 1 hingga 100 karakter")
    private String name;

    @NotNull(message = "areaId harus diisi")
    private UUID areaId;

    @NotNull(message = "Status harus diisi")
    private Byte isActive;

}
