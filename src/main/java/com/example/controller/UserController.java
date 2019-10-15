package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.User;
import com.example.form.UserForm;
import com.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public UserForm setUpUserForm() {
		return new UserForm();
	}
	
	@Autowired
	private HttpSession session;

	@RequestMapping("")
	public String index() {
		return "login";
	}
	
	@RequestMapping("/login")
	public String login(UserForm form,BindingResult result,Model model,RedirectAttributes redirectAttributes) {
//		User user = new User();
//		user.setEmail(form.getEmail());
//		user.setPassword(form.getPassword());
//		user = userService.login(user);
//		if (user == null) {
//			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが違います");
//			return index();
//		}
//		Integer userId = user.getId();
//		System.out.println(userId);
//		redirectAttributes.addAttribute("userId", userId);
		return "item_list";
	}
	
	@RequestMapping("/insert")
	public String index2() {
		return "register_user";
	}
	
	@RequestMapping("/toInsert")
	public String insert(@Validated UserForm form, BindingResult result) {
		
		if (!(form.getPassword().equals(form.getConfirmationPassword()))) {
			result.rejectValue("password", "", "パスワードが違います");
		}
		
		if (result.hasErrors()) {
			return index2();
		}
		User user = new User();
		BeanUtils.copyProperties(form, user);
		userService.insert(user);
		return "login";
	}
}
