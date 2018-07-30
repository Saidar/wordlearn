package com.application.util;

import java.util.List;

import com.application.word.model.*;

public class UserUtil {

	private List<User_word> users;

	public UserUtil() {
	}

	public UserUtil(List<User_word> users) {
		this.users = users;
	}

	public void setUsers(List<User_word> users) {
		this.users = users;
	}

	public boolean containsLogin( String login) {
		boolean contains = false;
		int i = 0;
		while(i < this.users.size()) {
			if(this.users.get(i).getLogin().equals(login))
				contains = true;
			i++;
		}
		return contains;
	}



}
