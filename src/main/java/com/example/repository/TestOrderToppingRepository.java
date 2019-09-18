package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;
import com.example.domain.Topping;

@Repository
public class TestOrderToppingRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("ordertop_id"));
		orderTopping.setToppingId(rs.getInt("ordertop_topping_id"));
		orderTopping.setOrderItemId(rs.getInt("ordertop_order_item_id"));
		Topping topping = new Topping();
		topping.setId(rs.getInt("topping_id"));
		topping.setName(rs.getString("topping_name"));
		topping.setPriceM(rs.getInt("topping_price_m"));
		topping.setPriceL(rs.getInt("topping_price_l"));
		orderTopping.setTopping(topping);
		return orderTopping;
	};
	
	public void InsertToppingId(OrderTopping orderTopping) {
		String sql = "INSERT INTO order_toppings (topping_id, order_item_id) VALUES (:toppingId,1);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql, param);
	}
	
	public List<OrderTopping> findPackTopping() {
		String sql = "SELECT ordertop.id ordertop_id, ordertop.topping_id ordertop_topping_id, ordertop.order_item_id ordertop_order_item_id, topping.id topping_id, topping.name topping_name, topping.price_m topping_price_m, topping.price_l topping_price_l FROM order_toppings ordertop LEFT OUTER JOIN toppings topping ON ordertop.topping_id = topping.id ORDER BY topping.id;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<OrderTopping> orderToppingList = template.query(sql, param, ORDER_TOPPING_ROW_MAPPER);
		return orderToppingList;
	}
	
	public void delete() {
		String sql = "DELETE FROM order_toppings;";
		SqlParameterSource param = new MapSqlParameterSource();
		template.update(sql, param);
	}
	
//	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
//	
//		//大元の入れ物になるOrderオブジェクトを作る
//		Order order = new Order();
//		
//		//これからResultSetExtractorで値を入れていくが、orderItemListのみ別入れになるのでこの段階では何も入ってない空のListを用意して入れておく
//		List<OrderItem> orderItemList = new ArrayList<>();
//		order.setOrderItemList(orderItemList);
//		List<OrderTopping> orderToppingList = null;
//		
//		int preOrderId = -1;
//		int preOrderItemId = -1;
//		int preOrderToppingId = -1;
//
//		while (rs.next()) {
//			
//		}	
		
}
