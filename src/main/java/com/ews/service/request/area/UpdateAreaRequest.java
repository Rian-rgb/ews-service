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

    @NotBlank(message = "Name harus diisi")
    @Size(max = 100, message = "Name tidak boleh lebih dari 100 karakter")
    private String name;

    @NotNull(message = "isActive harus diisi")
    private Byte isActive;

}
