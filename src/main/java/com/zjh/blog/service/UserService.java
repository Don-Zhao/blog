package com.zjh.blog.service;

import com.zjh.blog.po.User;

public interface UserService {
	
	User checkUser(String username, String password);
}
