package com.reflecty.mainClasses;

public class MySqlDatabaseConnector implements DatabaseConnector {

    private MySqlDatabaseConnectionProvider provider;

    public MySqlDatabaseConnector(MySqlDatabaseConnectionProvider provider) {
        this.provider = provider;
    }

    @Override
    public String executeQuery() {
        return provider.getInstance().executeQuery();
    }
}
