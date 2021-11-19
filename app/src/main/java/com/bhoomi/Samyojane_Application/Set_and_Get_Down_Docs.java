package com.bhoomi.Samyojane_Application;

/**
 * Created by Adarsh on 03-Jun-19.
 */

public class Set_and_Get_Down_Docs {

    private String GSCNO, DocumentName, Document;
    private int DocumentID;

    public String getGSCNO() {
        return GSCNO;
    }

    public void setGSCNO(String GSCNO) {
        this.GSCNO = GSCNO;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getDocument() {
        return Document;
    }

    public void setDocument(String document) {
        Document = document;
    }

    public int getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(int documentID) {
        DocumentID = documentID;
    }
}
