package com.example.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;

@Repository
public class FullOrderItemRepository {
	
	private SimpleJdbcInsert insert;
	
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * OrderItemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<OrderItem> ORDER_ROW_MAPPER = (rs, i) -> {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(rs.getInt("id"));
		orderItem.setItemId(rs.getInt("item_id"));
		orderItem.setOrderId(rs.getInt("order_id"));
		orderItem.setQuantity(rs.getInt("quantity"));
		String ch = rs.getString("size");
		orderItem.setSize(ch.charAt(0));
		return orderItem;
	};
	
	public Integer insert(OrderItem orderItem) {
		String sql = "INSERT INTO order_items(item_id, order_id, quantity, size) \n" + 
				"VALUES(:itemId, :orderId, :quantity, :size);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		return key.intValue();
	}
	
	public void delete(Integer itemId) {
		String sql = "DELETE FROM order_items WHERE item_id = :itemId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		template.update(sql.toString(), param);
	}
	
//	public OrderItem select() {
//		String sql = "SELECT id,name,description,price_m,price_l,image_path,deleted FROM items";
//		SqlParameterSource param = new MapSqlParameterSource();
//		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
//		return itemList;
//	}
}
