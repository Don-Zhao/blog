package com.zjh.blog.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zjh.blog.po.Tag;

public interface TagService {
	
	Tag saveTag(Tag tag);
	
	Tag getTag(Long id);
	
	Page<Tag> listTag(Pageable pageable);
	
	Tag updateTag(Long id, Tag tag);
	
	void deleteTag(Long id);
	
	Tag findByName(String name);
	
	List<Tag> listAllTag();
	
	List<Tag> listTagsByIds(String ids);
	
	List<Tag> listTagTop(Integer size);
}
