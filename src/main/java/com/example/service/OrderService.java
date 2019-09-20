package com.example.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.OrderItemForm;
import com.example.form.ShoppingCartForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	public Order findByOrderId(Integer id) {
		return orderRepository.findByOrderId(id);
	}
	
	public void insert(Order order) {
		orderRepository.insert(order);
	}
	
	public void delete(Integer id) {
		orderRepository.deleted(id);
	}
	
	public void updateUserId(Integer id, Integer userId) {
		orderRepository.updateUserId(id, userId);
	}
	
	public void update(OrderItemForm form) {
		Order order = orderRepository.findByOrderId(form.getIntId());
		BeanUtils.copyProperties(form, order);
		Date date = new Date();
		order.setOrderDate(date);
		if ("1".equals(form.getPaymentMethod())) {
			order.setStatus(1);
		} else {
			order.setStatus(2);
		}
		order.setDeliveryTime(form.getTimestampDeliveryDayTime());
		order.setPaymentMethod(form.getIntePaymentMethod());
		order.setTotalPrice(form.getIntTotalPrice());
		orderRepository.update(order);
	}
	
	public Item findByItemId(Integer itemId) {		
		return orderRepository.findByItemId(itemId);
	}
	
	/**
	 * OrderItemオブジェクトを生成する処理を行います.
	 * 
	 * @param form リクエストパラメータ
	 * @param orderId 主キー
	 * @return OrderItemオブジェクト
	 */
	private OrderItem createOrderItem(ShoppingCartForm form, Integer orderId) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getIntItemId());
		orderItem.setOrderId(orderId);
		orderItem.setQuantity(form.getIntQuantity());
		orderItem.setSize(form.getCharSize());
		return orderItem;
	}
	
	@SuppressWarnings("null")
	public Order insert(ShoppingCartForm form) {
		
		Order checkOrder = orderRepository.findByUserIdAndStatus(form.getIntUserId(), form.getIntStatus());
		
		OrderItem orderItem = null;
		Integer orderId = null;
		
		if (checkOrder == null) {   //カートの中身が空の処理
			Order order = new Order();
			order.setUserId(form.getIntUserId());
			order.setStatus(form.getIntStatus());
			order.setTotalPrice(0);
			orderId = orderRepository.insert(order);

			orderItem = createOrderItem(form, orderId);
		} else { // カートの中身が有る場合
			orderId = checkOrder.getId();
			orderItem = createOrderItem(form, checkOrder.getId());
		}
		Integer orderItemId = orderItemRepository.insert(orderItem);
			
			OrderTopping orderTopping = null;
			if (form.getToppingIdList() == null) {
				form.setToppingIdList(new ArrayList<Integer>());
			}
			for (Integer toppingId : form.getToppingIdList()) {
				orderTopping = new OrderTopping();
				orderTopping.setToppingId(toppingId);
				orderTopping.setOrderItemId(orderItemId);
				orderToppingRepository.insert(orderTopping);
			}
		return orderRepository.findByOrderId(orderId);
	}
	
	public Order findByOrderId2(Integer id) {
		return orderRepository.findByOrderId2(id);
	}

	
}
