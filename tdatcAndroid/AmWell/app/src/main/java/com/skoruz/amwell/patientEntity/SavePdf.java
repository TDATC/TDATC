package com.skoruz.amwell.patientEntity;

/**
 * Created by SKoruz-Keerthi on 08-10-2015.
 */
public class SavePdf {

    private int  id;
    private String fileName;
    private String filePath;
    private String fileDescription;
    private String uploadDate;
    private int  patientId;
    private byte[] fileData;
    private int readUnread;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDescription() {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription) {
        this.fileDescription = fileDescription;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public int getReadUnread() {
        return readUnread;
    }

    public void setReadUnread(int readUnread) {
        this.readUnread = readUnread;
    }
}
