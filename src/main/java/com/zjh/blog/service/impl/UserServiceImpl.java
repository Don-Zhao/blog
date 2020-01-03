package com.zjh.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjh.blog.dao.UserRepository;
import com.zjh.blog.po.User;
import com.zjh.blog.service.UserService;
import com.zjh.blog.utils.MD5Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User checkUser(String username, String password) {
		User user = userRepository.findByUsernameAndPassword(username, MD5Utils.getMD5(password));
		if (user != null) {
			user.setPassword(null);
		}
		return user;
	}

}
