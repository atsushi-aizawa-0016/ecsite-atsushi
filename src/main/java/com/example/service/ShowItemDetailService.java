package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.repository.ShowItemDetailRepository;
import com.example.repository.ToppingRepository;

@Service
public class ShowItemDetailService {

	@Autowired
	private ShowItemDetailRepository showItemDetailRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	public Item showDetail(Integer id) {
		Item item = showItemDetailRepository.load(id);
		List<Topping> toppingAllList = toppingRepository.load();
		item.setToppingList(toppingAllList);
		return item;
	}
}
