package com.lsw.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.lsw.springcloud.dao.UserDao;
import com.lsw.springcloud.entity.PageResultBean;
import com.lsw.springcloud.entity.User;
import com.lsw.springcloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean addUser(User user) {
		boolean flag;
		flag = userDao.addUser(user);
		return flag;
	}

	@Override
	public User getUser(int id) {
		User user = userDao.getUser(id);
		System.out.println("microservice-provider微服务在响应客户端请求……");
		System.out.println("user : " + user);
		return user;
	}

	@Override
	public List<User> getUsers() {
		List<User> users = userDao.getUsers();
		System.out.println("microservice-provider微服务在响应客户端请求……");
		return users;
	}
	
	@Override
	public PageResultBean<User> selectUser() {
	    // 这里写死pageNum和PageSize
	    // 排序实现: 数据库字段 + " desc" 或 数据库字段 + " asc"
	    PageHelper.startPage(1, 10, "create_time desc");
	    return new PageResultBean<User>(userDao.getUsers());
	}

}