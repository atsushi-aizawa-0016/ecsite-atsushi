package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.domain.User;
import com.example.service.ItemService;
import com.example.service.ToppingService;
import com.example.service.UserService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private HttpSession session;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	public ToppingService toppingService;
	
	@Autowired
	public UserService userService;
	
	@RequestMapping("/showItemList")
	public String showItemList(Model model,Integer userId) {
		List<Item> itemList = itemService.findAll();
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
		User user = new User();
		user = userService.findByUserId(userId);
		model.addAttribute("user", user);
		return "item_list";
	}
	
	@RequestMapping("/showItemDetail")
	public String showItemDetail(Integer id,Model model,Integer userId) {
		User user = new User();
		user = userService.findByUserId(userId);
		Item item = itemService.findById(id);
		
		List<Topping> toppingList = toppingService.findAll();
		List<List<Topping>> allToppingList = new ArrayList<>();
		List<Topping> topping3List = new ArrayList<>();
		for (int i = 1; i <= toppingList.size(); i++) {
			topping3List.add(toppingList.get(i - 1));
			if (i % 3 == 0) {
				allToppingList.add(topping3List);
				topping3List = new ArrayList<>(); 
			}
		}
		model.addAttribute("user", user);
		allToppingList.add(topping3List);
		item.setAllToppingList(allToppingList);;
		model.addAttribute("item", item);
		return "item_detail";
	}
}
