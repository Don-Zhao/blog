package com.zjh.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zjh.blog.po.Type;
import com.zjh.blog.service.BlogService;
import com.zjh.blog.service.TypeService;

@Controller
public class TypeShowController {

	@Autowired
	private TypeService typeService;
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/types/{id}")
	public String types(@PathVariable Long id, 
						@PageableDefault(size=2) Pageable pageable,
						Model model) {
		List<Type> types = typeService.listAllType();
		if (id == -1) {
			id = types.get(0).getId();
		}
		
		model.addAttribute("blogs", blogService.listBlog(id, pageable));
		model.addAttribute("types", types);
		model.addAttribute("activeTypeId", id);
		return "types";
	}
}
