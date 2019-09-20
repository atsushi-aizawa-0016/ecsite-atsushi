package com.example.form;

import java.util.List;

public class ItemForm {

	/** 商品ID */
	private String itemId;

	/** サイズ */
	private String size;

	/** 数量 */
	private String quantity;

	/** トッピングIDリスト */
	private List<Integer> toppingIdList;

	/** ユーザID */
	private String userId;

	/** 注文状態 */
	private String status;

	/** 仮ユーザID */
	private String preUserId;


	public Integer getTotalPrice() {
		return 0;
	}
	
	public Integer getIntPreUserId() {
		return Integer.parseInt(preUserId);
	}
	public String getPreUserId() {
		return preUserId;
	}
	public void setPreUserId(String preUserId) {
		this.preUserId = preUserId;
	}


	public List<Integer> getToppingIdList() {
		return toppingIdList;
	}
	public void setToppingIdList(List<Integer> toppingIdList) {
		this.toppingIdList = toppingIdList;
	}


	public Integer getIntUserId() {
		return Integer.parseInt(userId);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public char getCharSize() {
		return size.charAt(0);
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}


	public Integer getIntQuantity() {
		return Integer.parseInt(quantity);
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public Integer getIntItemId() {
		return Integer.parseInt(itemId);
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public Integer getIntStatus() {
		return Integer.parseInt(status);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "ExecuteShoppingCartForm [itemId=" + itemId + ", size=" + size + ", quantity=" + quantity
				+ ", toppingIdList=" + toppingIdList + ", userId=" + userId + ", status=" + status + "]";
	}
	
	
}
