package com.ews.service.request.risk;

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
public class UpdateRiskRequest {

    @NotBlank(message = "Code Name harus diisi")
    @Size(min = 1, max = 50, message = "Code Name harus antara 1 hingga 50 karakter")
    private String codeName;

    @NotBlank(message = "Description harus diisi")
    @Size(min = 1, max = 150, message = "Description harus antara 1 hingga 150 karakter")
    private String description;

    @NotNull(message = "keyProcessId harus diisi")
    private UUID keyProcessId;

    @NotNull(message = "riskStatusId harus diisi")
    private UUID riskStatusId;

    @NotNull(message = "isActive harus diisi")
    private Byte isActive;

}
