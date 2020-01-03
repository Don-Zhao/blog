package com.zjh.blog.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.zjh.blog.po.Comment;
import com.zjh.blog.po.User;
import com.zjh.blog.service.BlogService;
import com.zjh.blog.service.CommentService;

@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogService blogService;
	
	@Value("${comment.avator}")
	private String avator;
	
	@GetMapping("/comments/{blogId}")
	public String comment(@PathVariable Long blogId, Model model) {
		model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}
	
	@PostMapping("/comments")
	@Transactional
	public String post(Comment comment, HttpSession session) {
		Long blogId = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogId));
		User user = (User)session.getAttribute("user");
		if (user != null) {
			comment.setAvatar(user.getAvator());
			comment.setAdminComment(true);
			comment.setNickname(user.getNickname());
			comment.setEmail(user.getEmail());
		} else {
			comment.setAvatar(avator);
			comment.setAdminComment(false);
		}
		
		commentService.saveComment(comment);
		return "redirect:/comments/" + comment.getBlog().getId();
	}
}
