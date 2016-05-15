package com.reflecty.mainClasses;

import com.reflecty.annotations.Namespace;

public class MyFancyService {

    private final DatabaseConnector oracleDatabaseConnector;
    private final DatabaseConnector mySqlDatabaseConnector;

    public MyFancyService(
            @Namespace("oracle") DatabaseConnector oracleDatabaseConnector,
            @Namespace("mysql") DatabaseConnector mySqlDatabaseConnector
    ) {
        this.oracleDatabaseConnector = oracleDatabaseConnector;
        this.mySqlDatabaseConnector = mySqlDatabaseConnector;
    }

    public String queryTheOracleDatabase() {
        return oracleDatabaseConnector.executeQuery();
    }

    public String queryTheMySqlDatabase() {
        return mySqlDatabaseConnector.executeQuery();
    }
}
