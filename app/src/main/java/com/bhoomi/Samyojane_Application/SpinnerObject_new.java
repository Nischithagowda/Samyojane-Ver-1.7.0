package com.bhoomi.Samyojane_Application;

public class SpinnerObject_new {

    String databaseId;
    String databaseID1;
    String databaseValue;
    String str;

    public SpinnerObject_new(String databaseId, String databaseValue, String databaseID1) {
        this.databaseId = databaseId;
        this.databaseID1 = databaseID1;
        this.databaseValue = databaseValue;
    }

    public String getString(){
     return str;
    }

    public String getId () {
        return databaseId;
    }

    public String getID1(){
        return databaseID1;
    }

    public String getValue () {
        return databaseValue;
    }

    public String toString () {
        return databaseValue;
    }
}
