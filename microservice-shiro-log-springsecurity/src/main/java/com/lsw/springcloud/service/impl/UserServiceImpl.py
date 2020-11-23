package com.lsw.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lisw.springcloud.dao.UserDao;
import com.lisw.springcloud.entity.User2;
import com.lsw.springcloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User2 findByUsername(String username) {
		User2 user = userDao.findByUsername(username);
		return user;
	}
}