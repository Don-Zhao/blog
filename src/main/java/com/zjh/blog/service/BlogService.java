package com.zjh.blog.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zjh.blog.po.Blog;

public interface BlogService {
	Blog getBlog(Long id);
	
	Page<Blog> listBlog(Pageable pageable, Blog blog);
	
	Page<Blog> listBlog(Pageable pageable);
	
	Page<Blog> listBlog(String target, Pageable pageable);
	
	Blog saveBlog(Blog blog);
	
	Blog updateBlog(Long id, Blog blog);
	
	void deleteBlog(Long id);
	
	List<Blog> listRecommendBlogTop(Integer size);
	
	Blog updateViewsById(Long id);
	
	Page<Blog> listBlog(Long typeId, Pageable pageable);
	
	Page<Blog> findBlogByTagsId(Long tagsId, Pageable pageable);
	
	Map<String, List<Blog>> archiveBlog();
	
	Long getCount();
}
