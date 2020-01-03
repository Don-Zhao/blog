package com.zjh.blog.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	private String exceptionHandler(HttpServletRequest request, Exception e, Model model) throws Exception {
		logger.error("request URL: {}, Exception: {}", request.getRequestURL(), e);

		model.addAttribute("url", request.getRequestURL());
		model.addAttribute("exception", e);
		return "error/error";
	}
}
