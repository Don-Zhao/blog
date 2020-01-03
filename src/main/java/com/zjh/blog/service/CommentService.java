package com.zjh.blog.service;

import java.util.List;

import com.zjh.blog.po.Comment;

public interface CommentService {

	List<Comment> listCommentByBlogId(Long id);
	
	Comment saveComment(Comment comment);
}
