package com.example.enums;

/**
 * SQL文をまとめたEnum,
 * 
 * @author shota.suzuki
 *
 */
public enum SQL {

	INSERT(1, "INSERT INTO orders(user_id, status, total_price) VALUES(:userId, :status, :totalPrice);"),
	DELETE(2, "DELETE FROM orders WHERE id=:id;"),
	SELECT_ALL(3, "SELECT o1.id o1_id, o1.user_id o1_user_id, o1.status o1_status, o1.total_price o1_total_price, o1.order_date o1_order_date, o1.destination_name o1_destination_name, o1.destination_email o1_destination_email, o1.destination_zipcode o1_destination_zipcode, o1.destination_address o1_destination_address, o1.destination_tel o1_destination_tel, o1.delivery_time o1_delivery_time, o1.payment_method o1_payment_method,"
			+ "o2.id o2_id, o2.item_id o2_item_id, o2.order_id o2_order_id, o2.quantity o2_quantity, o2.size o2_size,"
			+ "o3.id o3_id, o3.topping_id o3_topping_id, o3.order_item_id o3_order_item_id,"
			+ "i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted,"
			+ "t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l "
			+ "FROM orders o1 FULL OUTER JOIN order_items o2 ON o1.id=o2.order_id "
			+ "FULL OUTER JOIN order_toppings o3 ON o2.id=o3.order_item_id "
			+ "FULL OUTER JOIN items i ON o2.item_id=i.id "
			+ "FULL OUTER JOIN toppings t ON o3.topping_id=t.id "),
	UPDATE(4, "UPDATE orders "
			+ "SET status=:status, order_date=:orderDate, total_price=:totalPrice, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode, destination_address=:destinationAddress, destination_tel=:destinationTel, delivery_time=:deliveryTime, payment_method=:paymentMethod "
			+ "WHERE id=:id;");
	
	private Integer key;
	private String value;
	
	
	private SQL(Integer key, String value) {
		this.key = key;
		this.value = value;
	}


	public Integer getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
	
	public static SQL of(Integer key) {
		for (SQL sql : SQL.values()) {
			if (sql.key == key) {
				return sql;
			}
		}
		throw new IndexOutOfBoundsException("値が存在しません");
	}

}
