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
	private double eventLong;
	private double eventLat;
	
	public User() {
		phoneNumber = null;
		userName = "Anonymous";
		isPublisher = false;
		eventLat = 0.0;
		eventLong = 0.0;
	}
	
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
	public double getEventLong() {
		return eventLong;
	}

	public void setEventLong(double eventLong) {
		this.eventLong = eventLong;
	}

	public double getEventLat() {
		return eventLat;
	}

	public void setEventLat(double eventLat) {
		this.eventLat = eventLat;
	}
	
}


