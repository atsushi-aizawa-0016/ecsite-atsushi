//package com.example.controller;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.example.domain.User;
//import com.example.form.RegisterUserForm;
//import com.example.service.RegisterUserService;
//
//@Controller
//@RequestMapping("/register")
//public class RegisterUserController {
//	
//	@Autowired
//	private RegisterUserService registerUserService;
//	
//	@ModelAttribute
//	public RegisterUserForm setUpRegisterUserForm() {
//		return new RegisterUserForm();
//	}
//	
//	@RequestMapping("/toInsert")
//	public String toInsert() {
//		return "register_user";
//	}
//	
//	@RequestMapping("/insert")
//	public String Insert(@Validated RegisterUserForm form, BindingResult result) {
//		
//		if (!(form.getPassword().equals(form.getConfirmationPassword()))) {
//			result.rejectValue("password", "", "パスワードが違います");
//		}
//		
//		if (result.hasErrors()) {
//			return toInsert();
//		}
//		
//		User user = new User();
//		// フォームからドメインにプロパティ値をコピー
//		BeanUtils.copyProperties(form, user);
//		registerUserService.insert(user);
//		return "redirect:/toLogin";
//	}
//
//}
