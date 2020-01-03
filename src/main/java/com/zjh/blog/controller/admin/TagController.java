package com.zjh.blog.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.zjh.blog.po.Tag;
import com.zjh.blog.service.TagService;

@Controller
@RequestMapping("/admin")
public class TagController {

	@Autowired
	private TagService tagService;
	
	@GetMapping("/tag/list")
	public String list(@PageableDefault(sort= {"id"}) Pageable pageable, Model model) {
		Page<Tag> page = tagService.listTag(pageable);
		model.addAttribute("tags", page);
		
		return "admin/tags";
	}
	
	@GetMapping("/tag/add")
	public String input(Model model) {
		model.addAttribute("tag", new Tag());
		return "admin/tag-input";
	}
	
	@PostMapping("/tag/save")
	public String saveTag(@Valid Tag tag, 
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "admin/tag-input";
		}
		
		if (tagService.findByName(tag.getName()) != null) {
			result.rejectValue("name", "name_error", "分类名称不能重复");
			return "admin/tag-input";
		}

		Tag t = tagService.saveTag(tag);
		if (t != null) {
			attributes.addFlashAttribute("message", "更新成功");
		} else {
			attributes.addFlashAttribute("message", "更新失败");
		}
		
		return "redirect:/admin/tag/list";
	}
	
	@GetMapping("/tag/del/{id}")
	public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
		tagService.deleteTag(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/tag/list";
	}
	
	@GetMapping("/tag/{id}/update")
	public String editInput(@PathVariable Long id, Model model) {
		model.addAttribute("tag", tagService.getTag(id));
		return "admin/tag-input";
	}
	
	@PostMapping("/tag/update/{id}")
	public String updateTag(@Valid Tag tag, 
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "admin/tag-input";
		}
		
		if (tagService.findByName(tag.getName()) != null) {
			result.rejectValue("name", "name_error", "分类名称不能重复");
			return "admin/tag-input";
		}

		Tag t = tagService.saveTag(tag);
		if (t != null) {
			attributes.addFlashAttribute("message", "更新成功");
		} else {
			attributes.addFlashAttribute("message", "更新失败");
		}
		
		return "redirect:/admin/tag/list";
	}
}
