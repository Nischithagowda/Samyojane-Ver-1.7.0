package com.bhoomi.Samyojane_Application;

public class SpinnerObject {

    private String databaseId;
    private String databaseValue;
    private String str;

    public SpinnerObject(String databaseId, String databaseValue) {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public String getString(){
     return str;
    }

    public String getId () {
        return databaseId;
    }

    public String getValue () {
        return databaseValue;
    }

    public String toString () {
        return databaseValue;
    }
}
