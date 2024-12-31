package com.ews.service.request.databasetype;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDatabaseTypeRequest {

    @NotBlank(message = "Name harus diisi")
    @Size(max = 100, message = "Name tidak boleh lebih dari 100 karakter")
    private String name;

    @NotBlank(message = "Connection Name harus diisi")
    @Size(max = 100, message = "Connection Name tidak boleh lebih dari 100 karakter")
    private String connectionName;

    @NotBlank(message = "Connection harus diisi")
    @Size(max = 100, message = "Connection tidak boleh lebih dari 100 karakter")
    private String connection;

    @NotBlank(message = "Db Host harus diisi")
    @Size(max = 100, message = "Db Host tidak boleh lebih dari 100 karakter")
    private String dbHost;

    @NotBlank(message = "Db Port harus diisi")
    @Size(max = 100, message = "Db Port tidak boleh lebih dari 100 karakter")
    private String dbPort;

    private String dbName;

    @NotBlank(message = "Db Username harus diisi")
    @Size(max = 100, message = "Db Username tidak boleh lebih dari 100 karakter")
    private String dbUserName;

    @NotBlank(message = "Db Password harus diisi")
    @Size(max = 100, message = "Db Password tidak boleh lebih dari 100 karakter")
    private String dbPass;

}
