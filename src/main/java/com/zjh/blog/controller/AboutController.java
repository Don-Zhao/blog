package com.zjh.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zjh.blog.service.TagService;
import com.zjh.blog.service.TypeService;

@Controller
public class AboutController {

	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("types", typeService.listTypeTop(5));
		model.addAttribute("tags", tagService.listTagTop(5));
		return "about";
	}
}
