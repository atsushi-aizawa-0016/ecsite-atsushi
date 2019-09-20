package com.example.form;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.validation.constraints.NotBlank;

/**
 * 注文情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class OrderItemForm {
	
	/** 注文ID */
	private String orderId;

	/** 注文日時 */
	private String orderDate;
	
	@NotBlank(message = "氏名を入力してください")
	/** 宛先氏名 */
	private String destinationName;
	
	@NotBlank(message = "メールアドレスを入力してください")
	/** 宛先Eメール */
	private String destinationEmail;
	
	@NotBlank(message = "郵便番号を入力してください")
	/** 宛先郵便番号 */
	private String destinationZipcode;
	
	@NotBlank(message = "住所を入力してください")
	/** 宛先住所 */
	private String destinationAddress;
	
	@NotBlank(message = "電話番号を入力してください")
	/** 宛先TEL */
	private String destinationTel;

	/** 配達日時 */
	private String deliveryDayTime; //これは入力チェックは入らない
	
	/** 支払方法 */
	private String paymentMethod;

	/** 合計金額 */
	private String totalPrice;
	
	@NotBlank(message = "配達日を選択してください")
	/** 配達日 */
	private String deliveryDay;
	
	/** 配達時間 */
	private String deliveryTime;
	
	
	public java.sql.Timestamp getTimestampDeliveryDayTime() {
		String DayTime = deliveryDay + "-" + deliveryTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
		LocalDateTime date = LocalDateTime.parse(DayTime, formatter);
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(date);
		return timestamp;
	}
	public String getDeliveryDayTime() {
		return deliveryDayTime;
	}
	public void setDeliveryDayTime(String deliveryDayTime) {
		this.deliveryDayTime = deliveryDayTime;
	}
	
	
	public String getDeliveryDay() {
		return deliveryDay;
	}
	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
	}
	
	
	public Integer getIntTotalPrice() {
		return Integer.parseInt(totalPrice);
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getIntId() {
		return Integer.parseInt(orderId);
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String id) {
		this.orderId = id;
	}

	public Date getDateOrderDate() {
		return null;
	};
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}


	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}


	public String getDestinationEmail() {
		return destinationEmail;
	}
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}


	public String getDestinationZipcode() {
		return destinationZipcode;
	}
	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}


	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}


	public String getDestinationTel() {
		return destinationTel;
	}
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}


	//未実装
	public Timestamp getTimeDeliveryTime() {
		return null;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getIntePaymentMethod() {
		return Integer.parseInt(paymentMethod);
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "OrderItemForm [orderId=" + orderId + ", orderDate=" + orderDate + ", destinationName=" + destinationName
				+ ", destinationEmail=" + destinationEmail + ", destinationZipcode=" + destinationZipcode
				+ ", destinationAddress=" + destinationAddress + ", destinationTel=" + destinationTel
				+ ", deliveryTime=" + deliveryTime + ", paymentMethod=" + paymentMethod + ", totalPrice=" + totalPrice
				+ "]";
	}

}
