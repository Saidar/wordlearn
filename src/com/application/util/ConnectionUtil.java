package com.application.util;

public class ConnectionUtil {

	private static String username = "root";
	private static String password = "rootroot";
	private static String dbString = "jdbc:mysql://localhost/learn_app?useSSL=false&useTimezone=true&serverTimezone=UTC";
	private static String dbName = "learn_app";

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
		ConnectionUtil.dbName = dbName;
		ConnectionUtil.dbString = "jdbc:mysql://localhost/" + dbName + "?useSSL=false&useTimezone=true&serverTimezone=UTC";
	}

	public static String getDbString() {
		return dbString;
	}

	public static void setDbString(String dbName) {
		ConnectionUtil.dbString = "jdbc:mysql://localhost/" + dbName + "?useSSL=false&useTimezone=true&serverTimezone=UTC";
	}

}
