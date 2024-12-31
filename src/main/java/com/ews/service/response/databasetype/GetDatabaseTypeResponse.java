package com.ews.service.response.databasetype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDatabaseTypeResponse {

    private UUID id;

    private String name;

    private String connectionName;

    private String connection;

    private String dbHost;

    private String dbPort;

    private String dbName;

    private String dbUserName;

}
