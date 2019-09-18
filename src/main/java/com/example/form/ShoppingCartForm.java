package com.example.form;

import java.util.List;

import com.example.domain.Topping;

public class ShoppingCartForm {

	private String name;
	private String size;
	private Integer price;
	private String quantity;
	private List<Topping> toppingList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public List<Topping> getToppingList() {
		return toppingList;
	}
	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}
	@Override
	public String toString() {
		return "ShoppingCartForm [name=" + name + ", size=" + size + ", price=" + price + ", quantity=" + quantity
				+ ", toppingList=" + toppingList + "]";
	}
	
}
