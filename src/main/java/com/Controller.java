package com;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@org.springframework.stereotype.Controller
public class Controller {
	
	@Autowired
	Repo repo;
	
	@GetMapping("/")
	public String showw(HttpSession session, Model model)
	{
		Student stu=new Student();
		if(session.getAttribute("dpt")!=null)
		{
			stu.setDepartment((String) session.getAttribute("dpt"));
		}
		model.addAttribute("std", stu);
		model.addAttribute("alldpts", List.of("it","cs","it2"));
		return "index";
		
		
	}
	
	
	@GetMapping("/submit")
	public String dm (@Valid @ModelAttribute("std") Student stu, BindingResult bindingResult, HttpSession session,Model model)
	{
		if(stu.getId()!=null&&repo.findById(stu.getId()).isPresent())
		{
			bindingResult.rejectValue("id", "ok", "ktrungid");
		}
		if(bindingResult.hasErrors())
		{
			model.addAttribute("std", stu);
			
			model.addAttribute("alldpts", List.of("it","cs","it2"));
			return "index";
			
			
		}
		repo.save(stu);
		session.setAttribute("dpt", stu.getDepartment());
		return "redirect:/";
		
		
		
		
		
	}
	
	
	
	

}
