package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.form.ShoppingcartForm;
import com.example.service.FullOrderItemService;
import com.example.service.FullOrderService;
import com.example.service.FullOrderToppingService;
import com.example.service.ItemService;

@Controller
@RequestMapping("/shoppingcart")
public class FullShoppingcartController {
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public ShoppingcartForm setUpShoppingcartForm() {
		return new ShoppingcartForm();
	}
	
	@Autowired
	private FullOrderService fullOrderService;
	@Autowired
	private FullOrderItemService fullOrderItemService;
	@Autowired
	private FullOrderToppingService fullOrderToppingService;
	@Autowired
	private ItemService itemService;
	
	/**
	 * Order,OrderItem,OrderTopping全部INSERTできるやつ
	 * 
	 * @param form
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/toCart")
	public String postOrder(ShoppingcartForm form,@AuthenticationPrincipal LoginUser loginUser, Integer orderId, Model model) {
		
//		Integer preUserId = 0;
//		if (loginUser == null) {
//			form.setUserId(preUserId);         一旦ログイン前提で考える
//		}
		
		
		//ここがorderのINSERT部分
		form.setUserId(loginUser.getUser().getId());
		User user = new User();
		Integer orderId2 = fullOrderService.insert(form, user);
		//ここからorderItemのINSERT部分
		Integer orderItemId = fullOrderItemService.insert(form, orderId2);
		//ここがorderToppingのINSERT部分
		fullOrderToppingService.insert(form,orderItemId);
		
		//ここから取り出し部分
		Order order = new Order();
		if (orderId == null) {
			order = fullOrderService.findByUserId(form.getUserId());
			
			Item item = new Item();
			item = itemService.findById(form.getIntItemId());
			
			int totalPrice = 0;
			if (form.getSize().equals("M")) {
				totalPrice = item.getPriceM() + 200 * form.getToppingIdList().size();
				order.setTotalPrice(totalPrice);
			}else {
				totalPrice = item.getPriceM() + 300 * form.getToppingIdList().size();
				order.setTotalPrice(totalPrice);
			}
			
			List<OrderItem> orderItemList = new ArrayList<>();;
			orderItemList = order.getOrderItemList();
			model.addAttribute("orderItemList", orderItemList);
			
			model.addAttribute("order", order);
			System.out.println("通常表示時のorder中身：" + order);
		}
		
		return "cart_list";
	}
	@RequestMapping("/delete")
	public String deleteItem(Integer itemId, Integer userId,Integer orderId, Model model) {
//		fullOrderItemService.delete(itemId);
		Order order = new Order();
		order = fullOrderService.findByUserId(userId);
//		order = fullOrderService.findByOrderId(orderId);
		System.out.println("delete時のorderのなかみ：" + order);
		List<OrderItem> orderItemList = new ArrayList<>();;
		orderItemList = order.getOrderItemList();
		model.addAttribute("orderItemList", orderItemList);
		model.addAttribute("order", order);
		return "cart_list";
	}
}
