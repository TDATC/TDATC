package com.skoruz.users.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table(name = "users")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	public User(int user_id, String firstName, String lastName,
			String emailAddress, String password, String user_type,
			String gender, String phonePersonel, String phoneBusiness,
			Integer fax, String address1, String address2, String address3,
			String country, String state, String city, String accountState,
			String lastLogin, String dateOfBirth, String resgistrationDate,
			String photoPath, Double longitude, Double latitude) {
		super();
		this.user_id = user_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.password = password;
		this.user_type = user_type;
		this.gender = gender;
		this.phonePersonel = phonePersonel;
		this.phoneBusiness = phoneBusiness;
		this.fax = fax;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.country = country;
		this.state = state;
		this.city = city;
		this.accountState = accountState;
		this.lastLogin = lastLogin;
		this.dateOfBirth = dateOfBirth;
		this.resgistrationDate = resgistrationDate;
		this.photoPath = photoPath;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int user_id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_address")
	private String emailAddress;

	private String password;

	@Column(name = "gender")
	private String gender;

	@Column(name = "user_type")
	private String user_type;

	@Column(name = "phone_personel")
	private String phonePersonel;

	@Column(name = "phone_business")
	private String phoneBusiness;

	@Column(name = "fax_number")
	private Integer fax;

	private String address1;
	private String address2;
	private String address3;
	private String country;
	private String state;
	private String city;

	@Column(name = "account_state")
	private String accountState;

	@Column(name = "last_login")
	private String lastLogin;

	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@Column(name = "registration_date")
	private String resgistrationDate;

	@Lob
	@Column(name = "photo_path")
	private String photoPath;

	private Double longitude;

	private Double latitude;
	
	/*@OneToMany(fetch=FetchType.EAGER ,mappedBy="user")
	@JsonManagedReference
	private Set<PhysicianDetails> physicianDetails=new HashSet<PhysicianDetails>();
	
	
	@OneToMany(fetch=FetchType.EAGER ,mappedBy="specializations")
	@JsonManagedReference
	private Set<PhysicianDetails> specializationdetails=new HashSet<PhysicianDetails>();
	
	
	
	

	public Set<PhysicianDetails> getSpecializationdetails() {
		return specializationdetails;
	}

	public void setSpecializationdetails(Set<PhysicianDetails> specializationdetails) {
		this.specializationdetails = specializationdetails;
	}*/

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonePersonel() {
		return phonePersonel;
	}

	public void setPhonePersonel(String phonePersonel) {
		this.phonePersonel = phonePersonel;
	}

	public String getPhoneBusiness() {
		return phoneBusiness;
	}

	public void setPhoneBusiness(String phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
	}

	public Integer getFax() {
		return fax;
	}

	public void setFax(Integer fax) {
		this.fax = fax;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResgistrationDate() {
		return resgistrationDate;
	}

	public void setResgistrationDate(String resgistrationDate) {
		this.resgistrationDate = resgistrationDate;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/*public Set<PhysicianDetails> getPhysicianDetails() {
		return physicianDetails;
	}

	public void setPhysicianDetails(Set<PhysicianDetails> physicianDetails) {
		this.physicianDetails = physicianDetails;
	}*/
}
