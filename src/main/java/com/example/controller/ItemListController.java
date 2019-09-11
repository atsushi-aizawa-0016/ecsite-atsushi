package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ItemListService;

@Controller
@RequestMapping("")
public class ItemListController {
	
	@Autowired
	private ItemListService itemListService;

	@RequestMapping("/showItemList")
	public String showItemList(Model model, String searchName) {
		List<Item> itemList = itemListService.showItemList(searchName);
		//リストが入るリストを作る
		List<List<Item>> allItemList = new ArrayList<>();
		//3つの商品情報が入るリスト
		List<Item> item3List = new ArrayList<>();
		
		for (int i = 1; i <= itemList.size(); i++) {
			item3List.add(itemList.get(i - 1));
			if (i % 3 == 0) {
				allItemList.add(item3List);
				item3List = new ArrayList<>(); 
			}
		}
		allItemList.add(item3List);
		model.addAttribute("allItemList", allItemList);
		return "item_list";
	}
}
