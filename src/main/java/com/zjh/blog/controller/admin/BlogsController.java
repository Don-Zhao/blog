package com.zjh.blog.controller.admin;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zjh.blog.exception.NotFindException;
import com.zjh.blog.po.Blog;
import com.zjh.blog.po.Tag;
import com.zjh.blog.po.User;
import com.zjh.blog.service.BlogService;
import com.zjh.blog.service.TagService;
import com.zjh.blog.service.TypeService;

@Controller
@RequestMapping("/admin")
public class BlogsController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/blogs")
	public String list(@PageableDefault Pageable pageable, Blog blog, Model model) {
		model.addAttribute("types", typeService.listAllType());
		model.addAttribute("blogs", blogService.listBlog(pageable, blog));
		return "admin/blogs";
	}
	
	@PostMapping("/blogs/search")
	public String search(@PageableDefault Pageable pageable, Blog blog, Model model) {
		if (blog.getTypeId() != null && !blog.getTypeId().isEmpty()) {
			blog.setType(typeService.getType(Long.valueOf(blog.getTypeId())));
		}
		model.addAttribute("blogs", blogService.listBlog(pageable, blog));
		return "admin/blogs::blogList";
	}
	
	@GetMapping("/blog/add")
	public String blogInput(Model model) {
		Blog blog = new Blog();
		model.addAttribute("blog", blog);
		
		model.addAttribute("tags", tagService.listAllTag());
		model.addAttribute("types", typeService.listAllType());
		
		return "admin/blog-input";
	}
	
	@PostMapping("/blog/save")
	@Transactional
	public String saveBlog(@Valid Blog blog, BindingResult result, HttpSession session, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "admin/blog-input";
		}
		
		Blog b = null;
		blog.setUpdateTime(new Date());
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagService.listTagsByIds(blog.getTagIds()));
		
		if (blog.getId() != null) {
			b = blogService.updateBlog(blog.getId(), blog);
		} else {
			User user = (User)session.getAttribute("user");
			blog.setUser(user);
			blog.setCreateTime(new Date());
			blog.setViews(0);
			b = blogService.saveBlog(blog);
		}
		
		if (b == null) {
			attributes.addFlashAttribute("message", "操作失败");
		} else {
			attributes.addFlashAttribute("message", "操作成功");
		}
		
		return "redirect:/admin/blogs";
	}
	
	@GetMapping("/blog/del/{id}")
	@Transactional
	public String deleteBlog(@PathVariable Long id, RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "删除操作成功");
		return "redirect:/admin/blogs";
	}
	
	@GetMapping("/blog/{id}/update")
	public String toUpdateBlog(@PathVariable Long id, Model model) {
		Blog blog = blogService.getBlog(id);
		if (blog == null) {
			throw new NotFindException("该博客不存在");
		}
		
		String ids = "";
		if (blog.getTags() != null && !blog.getTags().isEmpty()) {
			for (Tag tag : blog.getTags()) {
				ids += tag.getId();
				ids += ",";
			}
			
			blog.setTagIds(ids.substring(0, ids.length() - 1));
		}
		
		model.addAttribute("blog", blog);
		
		model.addAttribute("tags", tagService.listAllTag());
		model.addAttribute("types", typeService.listAllType());
		return "admin/blog-input";
	}
}
