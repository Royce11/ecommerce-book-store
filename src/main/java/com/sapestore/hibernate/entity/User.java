package com.sapestore.hibernate.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="SAPESTORE_MEMBERS_INFO")
@NamedQueries(value = {
		@NamedQuery(name = "User.findByName", query = "from User u where u.name = :name"),
		@NamedQuery(name = "User.findByUserIdandPassword", query = "from User u where u.userId = :userId and u.password = :password") })
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5605441280823670283L;
	
	@Id
	@Column(name="USER_ID")
	private String userId;	
	
	@Column(name="PASSWORD_HASH")
	private String password;
	
	@Column(name="ADDRESS_ID")
	private Integer addressId;	
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="GENDER_ID")
	private Integer genderId;	
	
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;	
	
	@Column(name="MOBILE_NUMBER")
	private String mobileNumber;	
	
	@Column(name="USER_STATUS")
	private String userStatus;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;	
	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;	
	
	@Column(name="IS_ADMIN")
	private String isAdmin;
	
	@Column(name="IS_ACTIVE")
	private String isActive;
	
	@Column(name="PHONE")
	private String phone;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * @return the isAdmin
	 */
	public String getIsAdmin() {
		return isAdmin;
	}

	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the genderId
	 */
	public Integer getGenderId() {
		return genderId;
	}

	/**
	 * @param genderId the genderId to set
	 */
	public void setGenderId(Integer genderId) {
		this.genderId = genderId;
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
