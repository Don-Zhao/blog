package com.zjh.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zjh.blog.po.Tag;
import com.zjh.blog.service.BlogService;
import com.zjh.blog.service.TagService;

@Controller
public class TagShowController {

	@Autowired
	private TagService tagService;
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/tags/{id}")
	public String types(@PathVariable Long id, 
						@PageableDefault(size=2) Pageable pageable,
						Model model) {
		List<Tag> tags = tagService.listAllTag();
		if (id == -1) {
			id = tags.get(0).getId();
		}
		
		model.addAttribute("blogs", blogService.findBlogByTagsId(id, pageable));
		model.addAttribute("tags", tags);
		model.addAttribute("activeTagId", id);
		return "tags";
	}
}
