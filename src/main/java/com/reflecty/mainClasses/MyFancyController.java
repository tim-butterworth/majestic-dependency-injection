package com.reflecty.mainClasses;

public class MyFancyController {

    private MyFancyService myFancyService;

    public MyFancyController(MyFancyService myFancyService) {
        this.myFancyService = myFancyService;
    }

    public String queryTheDatabase() {
        String mySqlString = myFancyService.queryTheMySqlDatabase();
        String oracleString = myFancyService.queryTheOracleDatabase();
        return "MySql: -> "+mySqlString+"\n"+"OracleSql: -> "+oracleString;
    }
}
