package com.example.helper;

import com.example.entity.userendpoint.model.User;
/**
 * A class to store global variables which need to be reused throughout the application
 * @author
 *
 */
public class MyGlobal {
	// The global location object which will have the user's last known location at all times.
	public static GPSTracker currentLoc = null;
	public static User currentUser = null;

}
