package com.example.entity;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PhoneNumber;

/**
 * Class representing a single user.
 * @author Nik
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key key;
	private PhoneNumber phoneNumber;
	private String userName;
	private Boolean isPublisher;
	private double userLong;
	private double userLat;
	
/*	public User() {
		phoneNumber = null;
		userName = "Anonymous";
		isPublisher = false;
		userLat = 0.0;
		userLong = 0.0;
	}*/
	
	/**
	 * Key is read only.
	 * @return Key value
	 */
	public Key getKey() {
		return key;
	}
	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsPublisher() {
		return isPublisher;
	}

	public void setIsPublisher(Boolean isPublisher) {
		this.isPublisher = isPublisher;
	}
	public double getUserLong() {
		return userLong;
	}

	public void setUserLong(double userLong) {
		this.userLong = userLong;
	}

	public double getUsertLat() {
		return userLat;
	}

	public void setUserLat(double userLat) {
		this.userLat = userLat;
	}
	
}


