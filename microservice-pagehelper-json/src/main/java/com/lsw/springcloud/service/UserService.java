package com.lsw.springcloud.service;

import java.util.List;

import com.lsw.springcloud.entity.PageResultBean;
import com.lsw.springcloud.entity.User;

public interface UserService {

	public boolean addUser(User user);

	public User getUser(int id);

	public List<User> getUsers();

	PageResultBean<User> selectUser();
}