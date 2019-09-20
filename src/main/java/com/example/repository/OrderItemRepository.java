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
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("order_items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
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
	
	/**
	 * 注文商品情報を取得します.
	 * 
	 * @param orderId 注文情報ID
	 * @return 注文商品情報
	 */
	public List<OrderItem> findByOrderId(Integer orderId) {
		String sql = "SELECT id, item_id, order_id, quantity, size FROM order_items WHERE order_id=:orderId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<OrderItem> orderItemList = template.query(sql, param, ORDER_ROW_MAPPER);
		return orderItemList;
	}
	
	/**
	 * 挿入処理を行います.
	 * 
	 * @param orderItem OrderItemオブジェクト
	 * @return 注文商品ID
	 */
	public int insert(OrderItem orderItem) {
		System.out.println("orderItem:" + orderItem);
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO order_items(item_id, order_id, quantity, size) ");
		sql.append("VALUES(:itemId, :orderId, :quantity, :size);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		Number key = insert.executeAndReturnKey(param);
		return key.intValue();
	}
	
	/**
	 * 削除処理を行います.
	 * 
	 * @param id 注文情報ID
	 */
	public void delete(Integer id) {
		String sql = "DELETE FROM order_items WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
