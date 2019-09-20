package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.enums.SQL;

@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * 注文情報を取得するリザルトセットエクストラクター.
	 */
	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) ->{
		Order order = new Order();
		
		List<OrderItem> orderItemList = new ArrayList<>();
		order.setOrderItemList(orderItemList);
		List<OrderTopping> orderToppingList = null;
		
		int preOrderId = 0;
		int preOrderItemId = 0;
		int preOrderToppingId = 0;
		
		while (rs.next()) {
			
			int orderId = rs.getInt("o1_id");
			if (preOrderId != orderId) {
				
				System.out.println("orderIdとpreOrderIdが違うならばOrderが追加される。 orderId：" + orderId + "preOrderId：" + preOrderId);
				System.out.println("");
				 
				order.setId(rs.getInt("o1_id"));
				order.setUserId(rs.getInt("o1_user_id"));
				order.setStatus(rs.getInt("o1_status"));
				order.setTotalPrice(rs.getInt("o1_total_price"));
				order.setOrderDate(rs.getDate("o1_order_date"));
				order.setDestinationName(rs.getString("o1_destination_name"));
				order.setDestinationEmail(rs.getString("o1_destination_email"));
				order.setDestinationZipcode(rs.getString("o1_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o1_destination_address"));
				order.setDestinationTel(rs.getString("o1_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o1_delivery_time"));
				order.setPaymentMethod(rs.getInt("o1_payment_method"));
				
			}
			preOrderId = orderId;
			
			int orderItemId = rs.getInt("o2_id");
			if (preOrderItemId != orderItemId) {
				System.out.println("orderItemIdとpreOrderItemIdが違うならばOrderのorderItemListにOrderItemの情報が追加される。 orderItemId：" + orderItemId + "preOrderItemId：" + preOrderItemId);
				System.out.println("");
				
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("o2_id"));
				orderItem.setItemId(rs.getInt("o2_item_id"));
				orderItem.setOrderId(rs.getInt("o2_order_id"));
				orderItem.setQuantity(rs.getInt("o2_quantity"));
				orderItem.setSize(rs.getString("o2_size").charAt(0));
				
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));
				
				orderItem.setItem(item);
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);	
			}
			preOrderItemId = orderItemId;
			
			int orderToppingId = rs.getInt("o3_id");
			if (orderToppingId != preOrderToppingId) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("o3_id"));
				orderTopping.setToppingId(rs.getInt("o3_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("o3_order_item_id"));
				
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			orderToppingId = preOrderToppingId;
		}
		return order;	
	};
	
	/**
	 * 注文情報を取得するローマッパー.
	 */
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};
	
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};
	
	/**
	 * 注文情報の取得.
	 * 
	 * @param id 注文ID
	 * @return 注文情報
	 */
	public Order findByOrderId(Integer id) {
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
	
	/**
	 * 挿入処理.
	 * 
	 * @param order 注文オブジェクト
	 */
	public Integer insert(Order order) {
		StringBuilder sql=new StringBuilder();
		sql.append(SQL.INSERT.getValue());
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		Number key = insert.executeAndReturnKey(param);
		return key.intValue();
	}
	
	/**
	 * 削除処理.
	 * 
	 * @param id
	 */
	public void deleted(Integer id) {
		String sql = "DELETE FROM orders WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
	
	/**
	 * 更新処理を行います.
	 * 
	 * @param order 注文オブジェクト
	 */
	public void update(Order order) {
		String sql = "UPDATE orders SET status=:status, order_date=:orderDate, total_price=:totalPrice, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode, destination_address=:destinationAddress, destination_tel=:destinationTel, delivery_time=:deliveryTime, payment_method=:paymentMethod WHERE id=:id;"; 
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql, param);
	}
	
	/**
	 * 更新処理を行います.
	 * 
	 * @param id
	 * @param userId
	 */
	public void updateUserId(Integer id, Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders ");
		sql.append("SET user_id=:id ");
		sql.append("WHERE user_id=:userId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("id", id);
		template.update(sql.toString(), param);
	}
	
	public Item findByItemId(Integer itemId) {
		String sql = "SELECT * FROM items WHERE id=:itemId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}

	public void insertByOrderItem(OrderItem orderItem) {
		String sql = "INSERT INTO order_items(item_id,order_id,quantity,size) VALUE(:itemId,:orderId,:quantity,:size);";
	}
	
	/**
	 * 注文情報を取得します.
	 * 
	 * @param userId 注文ID, status 注文状態
	 * @return 注文情報
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method ");
		sql.append("FROM orders WHERE user_id=:userId AND status=:status");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_ROW_MAPPER);

		if (orderList.size() != 0) {
			System.out.println("注文情報：" + orderList.get(0));
			return orderList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 注文情報を取得します.
	 * 
	 * @param id 注文ID
	 * @return 注文情報
	 */
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
