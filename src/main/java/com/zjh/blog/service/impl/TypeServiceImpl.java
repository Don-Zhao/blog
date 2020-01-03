package com.zjh.blog.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zjh.blog.dao.TypeRepository;
import com.zjh.blog.exception.NotFindException;
import com.zjh.blog.po.Type;
import com.zjh.blog.service.TypeService;

@Service
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeRepository typeRepository;
	
	@Override
	@Transactional
	public Type saveType(Type type) {
		typeRepository.save(type);
		return type;
	}

	@Override
	public Type getType(Long id) {
		Type type = typeRepository.getOne(id);
		return type;
	}

	@Override
	public Page<Type> listType(Pageable pageable) {
		return typeRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Type updateType(Long id, Type type) {
		Type exsitType =  typeRepository.getOne(id);
		if (exsitType == null) {
			throw new NotFindException("该分类不存在");
		}
		
		BeanUtils.copyProperties(type, exsitType);
		return typeRepository.save(exsitType);
	}

	@Override
	@Transactional
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
	}

	@Override
	public Type findByName(String name) {
		Type type = typeRepository.findByName(name);
		return type;
	}

	@Override
	public List<Type> listAllType() {
		return typeRepository.findAll();
	}

	@Override
	public List<Type> listTypeTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
		Pageable pageable = PageRequest.of(0, size, sort);
		return typeRepository.findTop(pageable);
	}

}
