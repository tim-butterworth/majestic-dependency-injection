package com.reflecty.mainClasses;

import com.reflecty.annotations.Singleton;

@Singleton
public class OracleDatabaseConnector implements DatabaseConnector {

    private OracleDatabaseConnectionProvider provider;

    public OracleDatabaseConnector(OracleDatabaseConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public String executeQuery() {
        return provider.getInstance().executeSomeQuery();
    }
}
