package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserForm {
	
	private String id;
	@NotBlank(message="名前入力必須")
	private String name;
	@Email(message="Eメール不正")
	@Size(min=1,max=127,message="1~127文字")
	private String email;
	@NotBlank( message = "パスワードを入力してください")
	private String password;
	@NotBlank( message = "郵便番号を入力してください")
	private String zipcode;
	@NotBlank( message = "住所を入力してください")
	private String address;
	@NotBlank( message = "電話番号を入力してください")
	private String telephone;
	@NotBlank( message = "パスワードを入力してください")
	private String confirmationPassword;
	
	public Integer getIntId() {
		return Integer.parseInt(id);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getConfirmationPassword() {
		return confirmationPassword;
	}
	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}
	@Override
	public String toString() {
		return "registerUserForm [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", zipcode=" + zipcode + ", address=" + address + ", telephone=" + telephone
				+ ", confirmationPassword=" + confirmationPassword + "]";
	}

	
	
}
