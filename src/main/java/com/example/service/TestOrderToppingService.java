package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.OrderTopping;
import com.example.repository.TestOrderToppingRepository;

@Service
public class TestOrderToppingService {

	@Autowired
	private TestOrderToppingRepository testOrderToppingRepository;
	
	public void InsertToppingId(OrderTopping orderTopping) {
		testOrderToppingRepository.InsertToppingId(orderTopping);
	}
	
	public List<OrderTopping> findPackTopping(){
		List<OrderTopping> orderToppingList = testOrderToppingRepository.findPackTopping();
		return orderToppingList;
	}

}
