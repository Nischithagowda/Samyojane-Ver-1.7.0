package com.bhoomi.Samyojane_Application;

/**
 * Created by Adarsh on 03-Jun-19.
 */

public class Set_and_Get_Down_Docs {

    private String UDT_GSC_No, UDT_GSCFirstPart, UDT_Document_Id;
    private byte[] UDT_File;

    public void setUDT_GSC_No(String UDT_GSC_No) {
        this.UDT_GSC_No = UDT_GSC_No;
    }
    public String getUDT_GSC_No() {
        return UDT_GSC_No;
    }

    public void setUDT_GSCFirstPart(String UDT_GSCFirstPart) {
        this.UDT_GSCFirstPart = UDT_GSCFirstPart;
    }
    public String getUDT_GSCFirstPart() {
        return UDT_GSCFirstPart;
    }

    public void setUDT_Document_Id(String UDT_Document_Id) {
        this.UDT_Document_Id = UDT_Document_Id;
    }
    public String getUDT_Document_Id() {
        return UDT_Document_Id;
    }

    public void setUDT_File(byte[] UDT_File) {
        this.UDT_File = UDT_File;
    }
    public byte[] getUDT_File() {
        return UDT_File;
    }
}
