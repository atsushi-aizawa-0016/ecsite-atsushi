package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.ShoppingcartForm;
import com.example.repository.OrderToppingRepository;

@Controller
@RequestMapping("")
public class ShoppingCartController {
	
	@ModelAttribute
	public ShoppingcartForm setUpSoppingCartForm() {
		return new ShoppingcartForm();
	}
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;

	@RequestMapping("defaultCartList")
	public String index() {
		return "cart_list";
	}
	
//	public String showCartList(Integer id,Model model) {
//		orderToppingRepository.orderByTopping(id);
//		System.out.println();
//	}
	
}
