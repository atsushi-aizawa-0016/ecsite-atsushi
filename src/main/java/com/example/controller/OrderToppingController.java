package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Topping;

@Controller
@RequestMapping("")
public class OrderToppingController {

	@RequestMapping("/shoppingcart")
	public String OrderTopping(Integer toppingIdList) {
		System.out.println(toppingIdList);
		return "cart_list";
	}
}
