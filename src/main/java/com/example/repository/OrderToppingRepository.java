package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

@Repository
public class OrderToppingRepository {

	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("id"));
		orderTopping.setToppingId(rs.getInt("toppin_id"));
		orderTopping.setOrderItemId(rs.getInt("order_item_id"));;
		return orderTopping;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<OrderTopping> orderByTopping(Integer id){
		String sql = "SELECT * FROM order_toppings ot RIGHT OUTER JOIN toppings t ON ot.id = t.id WHERE t.id=:t.id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("t.id", id);
		List<OrderTopping> toppingList = template.query(sql, param, ORDER_TOPPING_ROW_MAPPER);
		return toppingList;
	}
}
