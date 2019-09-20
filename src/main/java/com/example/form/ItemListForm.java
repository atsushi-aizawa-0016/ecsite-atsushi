package com.example.form;

public class ItemListForm {

	private String id;
	
	public Integer getIntId() {
		return Integer.parseInt("id");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemLiseForm [id=" + id + "]";
	}
	
	
}
