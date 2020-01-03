package com.zjh.blog.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjh.blog.po.Blog;
import com.zjh.blog.service.BlogService;
import com.zjh.blog.service.TagService;
import com.zjh.blog.service.TypeService;
import com.zjh.blog.utils.MarkDownUtils;

@Controller
public class IndexController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/")
	public String index(@PageableDefault(size = 5, sort= {"updateTime"}) Pageable pageable, Model model) {
		Page<Blog> blogs = blogService.listBlog(pageable);
		
		model.addAttribute("blogs", blogs);
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(6));
		model.addAttribute("topBlogs", blogService.listRecommendBlogTop(6));
		return "index";
	}
	
	@GetMapping("/blog/{id}")
	@Transactional
	public String blog(@PathVariable Long id, Model model) {
		Blog blog = blogService.updateViewsById(id);
		String content = MarkDownUtils.markDownToHtmlExtensions(blog.getContent());
		blog.setContent(content);
		model.addAttribute("blog", blog);
		return "blog";
	}
	
	@RequestMapping("/search")
	public String search(@PageableDefault(size = 5, sort= {"updateTime"}) Pageable pageable, @RequestParam String target, Model model) {
		model.addAttribute("target", target);
		Page<Blog> blogs = blogService.listBlog("%" + target + "%", pageable);
		model.addAttribute("blogs", blogs);
		
		return "search";
	}
	
	@GetMapping("/new/blogs")
	public String newBlogs(Model model) {
		model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
		return "_fragments :: newBlogList";
	}
}
