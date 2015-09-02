package com.kanokun.cts.dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kanokun.cts.entity.User;

public interface UserDao extends Dao<User, Long>, UserDetailsService {

	User findByName(String name);

}