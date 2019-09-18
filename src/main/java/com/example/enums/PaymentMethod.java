package com.example.enums;

/**
 * 支払方法のEnum,
 * 
 * @author shota.suzuki
 *
 */
public enum PaymentMethod {
	
	CASHONDELIVERY(1, "代金引換"),
	CREDITCARD(2, "クレジットカード");
	
	
	private Integer key;
	private String value;
	
	
	private PaymentMethod(Integer key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public Integer getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
	
	public static PaymentMethod of(Integer key) {
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			if (paymentMethod.key == key) {
				return paymentMethod;
			}
		}
		throw new IndexOutOfBoundsException("値が存在しません");
	}
	
}
