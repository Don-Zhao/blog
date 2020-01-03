package com.zjh.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.zjh.blog.po.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
	
	@Query("select b from Blog b where b.recommend = true")
	List<Blog> findTop(Pageable pageable);
	
	@Query("select b from Blog b where b.title like ?1 or b.content like ?1")
	Page<Blog> listBlog(String target, Pageable pageable);
	
	@Query("select b from Blog b where b.type.id = ?1")
	Page<Blog> findBlogByType(Long typeId, Pageable pageable);
	
	Page<Blog> findBlogByTagsId(Long tagsId, Pageable pageable);
	
	@Query("select function('date_format', b.createTime, '%Y') as year from Blog b group by function('date_format', b.createTime, '%Y') order by function('date_format', b.createTime, '%Y') asc")
	List<String> findYearGroupByYear();
	
	@Query("select b from Blog b where function('date_format', b.createTime, '%Y') = ?1")
	List<Blog> findBlogByYear(String year);
	
}
