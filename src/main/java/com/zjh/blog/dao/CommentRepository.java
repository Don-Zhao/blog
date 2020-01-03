package com.zjh.blog.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zjh.blog.po.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findByBlogIdAndParentIdNull(Long blogId, Sort sort);
	
}
