package com.ews.service.request.keyprocesses;

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
public class UpdateKeyProcessesRequest {

    @NotBlank(message = "Kunci Proses harus diisi")
    @Size(min = 1, max = 100, message = "Kunci Proses harus antara 1 hingga 100 karakter")
    private String name;

    @NotNull(message = "objectCategoryId harus diisi")
    private UUID objectCategoryId;

    @NotNull(message = "Status harus diisi")
    private Byte isActive;

}
