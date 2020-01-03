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

import com.zjh.blog.po.Type;
import com.zjh.blog.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {

	@Autowired
	private TypeService typeService;
	
	@GetMapping("/type/list")
	public String list(@PageableDefault(sort= {"id"}) Pageable pageable, Model model) {
		Page<Type> page = typeService.listType(pageable);
		model.addAttribute("types", page);
		
		return "admin/types";
	}
	
	@GetMapping("/type/add")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/type-input";
	}
	
	@PostMapping("/type/save")
	public String saveType(@Valid Type type, 
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "admin/type-input";
		}
		
		if (typeService.findByName(type.getName()) != null) {
			result.rejectValue("name", "name_error", "分类名称不能重复");
			return "admin/type-input";
		}

		Type t = typeService.saveType(type);
		if (t != null) {
			attributes.addFlashAttribute("message", "更新成功");
		} else {
			attributes.addFlashAttribute("message", "更新失败");
		}
		
		return "redirect:/admin/type/list";
	}
	
	@GetMapping("/type/del/{id}")
	public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/admin/type/list";
	}
	
	@GetMapping("/type/{id}/update")
	public String editInput(@PathVariable Long id, Model model) {
		model.addAttribute("type", typeService.getType(id));
		return "admin/type-input";
	}
	
	@PostMapping("/type/update/{id}")
	public String updateType(@Valid Type type, 
			BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "admin/type-input";
		}
		
		if (typeService.findByName(type.getName()) != null) {
			result.rejectValue("name", "name_error", "分类名称不能重复");
			return "admin/type-input";
		}

		Type t = typeService.saveType(type);
		if (t != null) {
			attributes.addFlashAttribute("message", "更新成功");
		} else {
			attributes.addFlashAttribute("message", "更新失败");
		}
		
		return "redirect:/admin/type/list";
	}
}
