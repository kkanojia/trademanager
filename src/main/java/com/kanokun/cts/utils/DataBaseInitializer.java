package com.kanokun.cts.utils;

import java.text.ParseException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.kanokun.cts.dao.UserDao;
import com.kanokun.cts.entity.User;

public class DataBaseInitializer {

	private UserDao userDao;

	private PasswordEncoder passwordEncoder;

	protected DataBaseInitializer() {
	}

	public DataBaseInitializer(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	public void initDataBase() throws ParseException {

		List<User> users = userDao.findAll();

		if (users == null || users.size() < 1) {
			User adminUser = new User("admin", this.passwordEncoder.encode("admin"));
			adminUser.addRole("user");
			adminUser.addRole("admin");
			this.userDao.save(adminUser);

		}

	}

}