package com.application.util;

public class ConnectionUtil {

	private static String username = "root";
	private static String password = "rootroot";
	private static String dbName = "jdbc:mysql://localhost/learn_app?useSSL=false&useTimezone=true&serverTimezone=UTC";

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		ConnectionUtil.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ConnectionUtil.password = password;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		ConnectionUtil.dbName = "jdbc:mysql://localhost/" + dbName + "?useSSL=false&amp;useTimezone=true&amp;serverTimezone=UTC";
	}

}
