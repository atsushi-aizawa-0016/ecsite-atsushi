package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.Item;
import com.example.domain.OrderItem;
import com.example.form.ShoppingcartForm;
import com.example.repository.FullOrderItemRepository;
import com.example.repository.ItemRepository;

@Service
public class FullOrderItemService {

	@ModelAttribute
	public ShoppingcartForm setUpShoppingcartForm() {
		return new ShoppingcartForm();
	}
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private FullOrderItemRepository fullOrderItemRepository;
	
	public Integer insert(ShoppingcartForm form,Integer orderId) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getIntItemId());
		orderItem.setOrderId(orderId); //
		orderItem.setQuantity(form.getIntQuantity());
		orderItem.setSize(form.getCharSize());
		orderItem.setItem(itemRepository.findById(form.getIntItemId()));
		return fullOrderItemRepository.insert(orderItem);
	}
	
	public void delete(Integer itemId) {
		fullOrderItemRepository.delete(itemId);
	}
}
