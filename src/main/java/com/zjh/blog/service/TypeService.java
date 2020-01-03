package com.zjh.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zjh.blog.po.Type;

public interface TypeService {
	
	Type saveType(Type type);
	
	Type getType(Long id);
	
	Page<Type> listType(Pageable pageable);
	
	Type updateType(Long id, Type type);
	
	void deleteType(Long id);
	
	Type findByName(String name);
	
	List<Type> listAllType();
	
	List<Type> listTypeTop(Integer size);
}
