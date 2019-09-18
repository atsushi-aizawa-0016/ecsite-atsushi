package com.example.enums;

/**
 * 注文状態のEnum,
 * 
 * @author shota.suzuki
 *
 */
public enum Status {
	
	BEFOREORDER(1, "注文前"),
	DEPOSITED(2, "入金済"),
	SHIPPED(3, "発送済"),
	CANCEL(4, "キャンセル");
	
	
	private Integer key;
	private String value;

	
	private Status(Integer key, String value) {
		this.key = key;
		this.value = value;
	}


	public Integer getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	
	
	public static Status of(Integer key) {
		for (Status status : Status.values()) {
			if (status.key == key) {
				return status;
			}
		}
		throw new IndexOutOfBoundsException("値が存在しません");
	}
}
