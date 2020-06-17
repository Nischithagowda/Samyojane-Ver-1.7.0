package com.bhoomi.Samyojane_Application;

public class AutoCompleteTextBox_Object {

    public  String databaseId;
    public  String databaseValue;

    public AutoCompleteTextBox_Object(String databaseId, String databaseValue){
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
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
