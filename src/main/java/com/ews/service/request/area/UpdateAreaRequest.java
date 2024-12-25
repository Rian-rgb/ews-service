package com.ews.service.request.area;

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
public class UpdateAreaRequest {

    @NotBlank(message = "Nama Bidang harus diisi.")
    @Size(min = 1, max = 100, message = "Nama Bidang harus antara 1 hingga 100")
    private String name;

    @NotNull(message = "Status harus diisi.")
    private Byte isActive;
}
