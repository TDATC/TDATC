package com.skoruz.amwell.patientEntity;

/**
 * Created by keerthi on 6/1/16.
 */
public class UserImage {

    private String fileName;
    private byte[] fileData;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
