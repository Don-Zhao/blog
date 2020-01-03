package com.zjh.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zjh.blog.dao.TagRepository;
import com.zjh.blog.exception.NotFindException;
import com.zjh.blog.po.Tag;
import com.zjh.blog.service.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagRepository tagRepository;
	
	@Override
	@Transactional
	public Tag saveTag(Tag tag) {
		tagRepository.save(tag);
		return tag;
	}

	@Override
	public Tag getTag(Long id) {
		Optional<Tag> tag = tagRepository.findById(id);
		return tag.get();
	}

	@Override
	public Page<Tag> listTag(Pageable pageable) {
		return tagRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Tag updateTag(Long id, Tag tag) {
		Tag exsitTag =  tagRepository.getOne(id);
		if (exsitTag == null) {
			throw new NotFindException("该分类不存在");
		}
		
		BeanUtils.copyProperties(tag, exsitTag);
		return tagRepository.save(exsitTag);
	}

	@Override
	@Transactional
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}

	@Override
	public Tag findByName(String name) {
		Tag tag = tagRepository.findByName(name);
		return tag;
	}

	@Override
	public List<Tag> listAllTag() {
		return tagRepository.findAll();
	}

	@Override
	public List<Tag> listTagsByIds(String ids) {
		String[] idArray = ids.split(",");
		List<Tag> tags = new ArrayList<>();
		for (String id : idArray) {
			Long tagId = Long.valueOf(id);
			Tag tag = tagRepository.getOne(tagId);
			tags.add(tag);
		}
		
		return tags;
	}

	@Override
	public List<Tag> listTagTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
		Pageable pageable = PageRequest.of(0, size, sort);
		return tagRepository.findTop(pageable);
	}

}
