package com.zjh.blog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zjh.blog.dao.BlogRepository;
import com.zjh.blog.exception.NotFindException;
import com.zjh.blog.po.Blog;
import com.zjh.blog.po.Type;
import com.zjh.blog.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Override
	public Blog getBlog(Long id) {
		Blog blog = blogRepository.getOne(id);
		if (blog == null) {
			throw new NotFindException("该博客不存在");
		}
		return blog;
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable, Blog blog) {
		return blogRepository.findAll(new Specification<Blog>() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if ("".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + blog.getTitle() + "%"));
				}
				
				if (blog.getType() != null && blog.getType().getId() != null) {
					predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getType().getId()));
				}
				
				if (blog.isRecommend()) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
				}
				
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	@Override
	public Blog saveBlog(Blog blog) {
		return blogRepository.save(blog);
	}

	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.getOne(id);
		if (b == null) {
			throw new NotFindException("对象不存在");
		}
		
		//BeanUtils.copyProperties(blog, b);
		blog.setCreateTime(b.getCreateTime());
		blog.setViews(b.getViews());
		blog.setUser(b.getUser());
		return blogRepository.save(blog);
	}

	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAll(pageable);
	}

	@Override
	public List<Blog> listRecommendBlogTop(Integer size) {
		Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
		Pageable pageable = PageRequest.of(0, size, sort);
		return blogRepository.findTop(pageable);
	}

	@Override
	public Page<Blog> listBlog(String target, Pageable pageable) {
		return blogRepository.listBlog(target, pageable);
	}

	@Override
	public Blog updateViewsById(Long id) {
		Blog b = blogRepository.getOne(id);
		if (b == null) {
			throw new NotFindException("该博客不存在");
		}
		
		b.setViews(b.getViews() + 1);
		
		return blogRepository.save(b);
	}

	@Override
	public Page<Blog> listBlog(Long typeId, Pageable pageable) {
		Page<Blog> blogs = blogRepository.findBlogByType(typeId, pageable);
		return blogs;
	}

	@Override
	public Page<Blog> findBlogByTagsId(Long tagsId, Pageable pageable) {
		Page<Blog> blogs = blogRepository.findBlogByTagsId(tagsId, pageable);
		return blogs;
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findYearGroupByYear();
		
		Map<String, List<Blog>> blogMap = new HashMap<String, List<Blog>>();
		for (String year : years) {
			blogMap.put(year, blogRepository.findBlogByYear(year));
		}
		
		return blogMap;
	}

	@Override
	public Long getCount() {
		return blogRepository.count();
	}

}
