package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.repository.ItemListRepository;

@Service
public class ItemListService {

	@Autowired
	private ItemListRepository itemListRepository;
	
	public List<Item> showItemList(String searchName){
		if (searchName == null) {
			return itemListRepository.findAll();
		}
		return itemListRepository.findByName(searchName);
	}
}
