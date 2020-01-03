package com.zjh.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zjh.blog.service.BlogService;

@Controller
public class ArchivesController {

	@Autowired
	private BlogService blogService;
	
	@GetMapping("/archives")
	public String archives(Model model) {
		model.addAttribute("archives", blogService.archiveBlog());
		model.addAttribute("totalCount", blogService.getCount());
		return "archives";
	}
}
