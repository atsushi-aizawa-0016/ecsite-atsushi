package com.example.repository;

import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;

@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) ->{
		
		Order order = new Order();
		
		List<OrderItem> orderItemList = new ArrayList<>();
		order.setOrderItemList(orderItemList);
		List<OrderTopping> orderToppingList = null;
		
		int preOrderId = 0;
		int preOrderItemId = 0;
		int preOrderToppingId = 0;
		
		while (rs.next()) {
			if (preOrderId != rs.getInt("o1_id")) {
				 
				order.setId(rs.getInt("o1_id"));
				order.setUserId(rs.getInt("o1_user_id"));
			}
		}
		
	};
	
	public Order findByOrderId2(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o1.id o1_id, o1.user_id o1_user_id, o1.status o1_status, o1.total_price o1_total_price, o1.order_date o1_order_date, o1.destination_name o1_destination_name, o1.destination_email o1_destination_email, o1.destination_zipcode o1_destination_zipcode, o1.destination_address o1_destination_address, o1.destination_tel o1_destination_tel, o1.delivery_time o1_delivery_time, o1.payment_method o1_payment_method,");
		sql.append("o2.id o2_id, o2.item_id o2_item_id, o2.order_id o2_order_id, o2.quantity o2_quantity, o2.size o2_size,");
		sql.append("o3.id o3_id, o3.topping_id o3_topping_id, o3.order_item_id o3_order_item_id,");
		sql.append("i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted,");
		sql.append("t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l ");
		sql.append("FROM orders o1 FULL OUTER JOIN order_items o2 ON o1.id=o2.order_id ");
		sql.append("FULL OUTER JOIN order_toppings o3 ON o2.id=o3.order_item_id ");
		sql.append("FULL OUTER JOIN items i ON o2.item_id=i.id ");
		sql.append("FULL OUTER JOIN toppings t ON o3.topping_id=t.id ");
		sql.append("WHERE o1.user_id=:id and status=0;");
//		sql.append("ORDER BY t.id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Order order = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}
}
