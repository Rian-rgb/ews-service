package com.ews.service.response.databasetype;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDatabaseTypeResponse {

    private String name;

    private String connectionName;

    private String connection;

    private String dbHost;

    private String dbPort;

    private String dbName;

    private String dbUserName;

}
