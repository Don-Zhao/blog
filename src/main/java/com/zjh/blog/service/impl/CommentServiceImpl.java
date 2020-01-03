package com.zjh.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zjh.blog.dao.CommentRepository;
import com.zjh.blog.po.Comment;
import com.zjh.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	private List<Comment> tempReplys = new ArrayList<>();
	
	@Override
	public List<Comment> listCommentByBlogId(Long id) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
		List<Comment> comments = commentRepository.findByBlogIdAndParentIdNull(id, sort);
		return eachComment(comments);
	}

	@Override
	@Transactional
	public Comment saveComment(Comment comment) {
		Long parentId = comment.getParent().getId();
		if (parentId != -1) {
			comment.setParent(commentRepository.getOne(parentId));
		} else {
			comment.setParent(null);
		}
		comment.setCreateTime(new Date());
		Comment c = commentRepository.save(comment);
		return c;
	}
	
	private List<Comment> eachComment(List<Comment> comments) {
		List<Comment> commentsView = new ArrayList<>();
		for (Comment comment : comments) {
			Comment c = new Comment();
			BeanUtils.copyProperties(comment, c);
			commentsView.add(c);
		}
		
		combineChildren(commentsView);
		return commentsView;
	}
	
	private void combineChildren(List<Comment> comments) {
		for (Comment comment : comments) {
			List<Comment> replysl = comment.getReplaies();
			for (Comment reply1 : replysl) {
				recursively(reply1);
			}
			
			comment.setReplaies(tempReplys);
			tempReplys = new ArrayList<>();
		}
	}
	
	private void recursively(Comment comment) {
		tempReplys.add(comment);
		if (comment.getReplaies().size() > 0) {
			List<Comment> replys = comment.getReplaies();
			for (Comment reply : replys) {
				tempReplys.add(reply);
				if (reply.getReplaies().size() > 0) {
					recursively(comment);
				}
			}
		}
	}

}
