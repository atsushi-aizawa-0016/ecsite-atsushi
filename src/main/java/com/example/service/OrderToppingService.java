package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.OrderTopping;
import com.example.repository.OrderToppingRepository;

@Service
public class OrderToppingService {

	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	public List<OrderTopping> orderByTopping(Integer id){
		return orderToppingRepository.orderByTopping(id);
	}
}
