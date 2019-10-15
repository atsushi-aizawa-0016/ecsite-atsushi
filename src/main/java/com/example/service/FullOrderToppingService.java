package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.OrderTopping;
import com.example.form.ShoppingcartForm;
import com.example.repository.FullOrderToppingRepository;
import com.example.repository.ToppingRepository;

@Service
public class FullOrderToppingService {
	
	@ModelAttribute
	public ShoppingcartForm setUpShoppingcartForm() {
		return new ShoppingcartForm();
	}

	@Autowired
	private FullOrderToppingRepository fullOrderToppingRepository;
	
	@Autowired
	private ToppingRepository toppiRepository;
	
	/**
	 * リクエストパラメーターからOrderToppingのリストを作成
	 * 
	 * @param form
	 * @return オーダートッピングリスト
	 */
	public void insert(ShoppingcartForm form,Integer orderItemId){
		OrderTopping orderTopping = new OrderTopping();
		for (int i = 0; i < form.getToppingIdList().size(); i++) {
			orderTopping.setToppingId(form.getToppingIdList().get(i));
			orderTopping.setOrderItemId(orderItemId);
			orderTopping.setTopping(toppiRepository.findById(form.getToppingIdList().get(i)));
			fullOrderToppingRepository.insert(orderTopping);
		}
	}
}
