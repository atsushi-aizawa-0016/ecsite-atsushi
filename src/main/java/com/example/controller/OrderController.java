package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.ItemForm;
import com.example.form.ShoppingCartForm;
import com.example.service.OrderService;

@Controller
@RequestMapping("/shoppingCart")
public class OrderController {

	@ModelAttribute
	public ItemForm setUpItemForm() {
		return new ItemForm();
	}
	
	@ModelAttribute
	public ShoppingCartForm setUpShoppingCartForm() {
		return new ShoppingCartForm();
	}
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping("/defaultShoppingCart")
	public String index(Integer orderId, Model model) {
		if (orderId == null || orderId == 0) {
			return "cart_list";
		}
		Order order = orderService.findByOrderId(orderId);
		System.out.println(order);
		model.addAttribute(order);
		return "cart_list";
	}
	
	/**
	 * 削除処理を行います.
	 * 
	 * @return ショッピングカート画面.
	 */
	@RequestMapping("/delete")
	public String delete(String orderId, String orderItemId, Model model) {
		orderService.delete(Integer.parseInt(orderItemId));
		return index(Integer.parseInt(orderId), model);
	}
	
	/**
	 * ショッピングカートの中身を表示する
	 * ヘッダーのショッピングカートを直接見に行く時の処理
	 * 
	 * @param userId ユーザID
	 * @param model リクエストスコープ
	 * @return ショッピングカート画面を出力します.
	 */
	@RequestMapping("/header")
	public String header(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		
		Integer preUserId = (Integer) session.getAttribute("preUserId");
		
		if (loginUser == null) {
			Order order = orderService.findByOrderId2(preUserId);
			if (order == null) {
				order = new Order();
				List<OrderItem> orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);
			}
			model.addAttribute("order", order);
			return "cart_list";
		}
		
		if (preUserId != null && preUserId == -1 && loginUser != null) {
			orderService.updateUserId(loginUser.getUser().getId(), preUserId);
		}
		
		Order order = orderService.findByOrderId2(loginUser.getUser().getId());
		if (order == null) {
			order = new Order();
			List<OrderItem> orderItemList = new ArrayList<>();
			order.setOrderItemList(orderItemList);
		}
		model.addAttribute("order", order);
		System.out.println(order);
		return "cart_list";
	}
	
	/**
	 * ショッピングカートに商品追加
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toShoppingCart")
	public String toShoppingCart(Integer itemId, ShoppingCartForm form, @AuthenticationPrincipal LoginUser loginUser, Model model) {
		
		Integer preUserId = (Integer) session.getAttribute("preUserId");
		Integer preOrderId = (Integer) session.getAttribute("preOrderId");
		
		Order order = null;
		
		if (loginUser == null) { //未ログイン時の処理
            if (preOrderId == null) { //カートに商品を入れる1回目の処理
                Random rnd = new Random();
                Integer token = rnd.nextInt(10000);
                form.setUserId(String.valueOf(token));
            } else { //カートに商品を入れる2回目の処理
                form.setUserId(String.valueOf(preUserId));
            }
            order = orderService.insert(form);
            session.setAttribute("preOrderId", order.getId());
            session.setAttribute("preUserId", order.getUserId());
        } else { //ログイン時の処理
            if (preUserId != null) { //未ログイン時にカートに入れた商品を上書きする処理
                orderService.updateUserId(preOrderId, loginUser.getUser().getId());
            }
            form.setUserId(String.valueOf(loginUser.getUser().getId()));
            order = orderService.insert(form);
        }
        model.addAttribute("order", order);
        return "cart_list";
    }
}
