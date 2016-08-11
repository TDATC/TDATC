package com.skoruz.users.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="save_pdf")
public class SavePdf   implements Serializable {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	

	public SavePdf() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public SavePdf(int id, int patientId, String fileName, String uploadDate,
			String filePath, byte[] fileData, int readUnread) {
		super();
		this.id = id;
		this.patientId = patientId;
		this.fileName = fileName;
		this.uploadDate = uploadDate;
		this.filePath = filePath;
		this.fileData = fileData;
		this.readUnread = readUnread;
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="patient_id")
	private int patientId;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="uploaded_date")
	private String uploadDate;
	
	@Transient
	private String filePath;
	
	@Transient
	private byte[] fileData;
	
	@Column(name="read_unread")
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
	public int getPatientId() {
		return patientId;
	}
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public int getReadUnread() {
		return readUnread;
	}
	public void setReadUnread(int readUnread) {
		this.readUnread = readUnread;
	}
	
	
	
	
	 

}
