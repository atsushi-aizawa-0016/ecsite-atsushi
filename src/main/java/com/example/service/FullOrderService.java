package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.form.ShoppingcartForm;
import com.example.repository.FullOrderRepository;

@Service
public class FullOrderService {

	@Autowired
	private FullOrderRepository fullOrderRepository;
	
	@ModelAttribute
	public ShoppingcartForm setUpShoppingcartForm() {
		return new ShoppingcartForm();
	}
	
	public Integer insert(ShoppingcartForm form,User user) {
		Order order = new Order();
		order.setUserId(form.getUserId());
		order.setStatus(form.getIntStatus());
		order.setTotalPrice(form.getTotalPrice());
		order.setUser(user);
		return fullOrderRepository.insert(order);
	}
	
	public Order findByUserId(Integer userId) {
		return fullOrderRepository.findByUserId(userId);
	}
	public Order findByOrderId(Integer orderId) {
		return fullOrderRepository.findByOrderId(orderId);
	}
}
