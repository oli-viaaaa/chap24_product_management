package com.javalab.dto;

import java.sql.Date;

public class Member {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String zip_num; 
	private String address;
	private String phone;
	private String useyn;
	private Date regdate;
	
	 //pagination field
	 private String pageNum = "1";	//페이지 번호
	 private String searchText = "";//조회 키워드
	 private Integer listCount = 10;	//1페이지당 게시물수
	 private Integer pagePerBlock = 5;	//한 번에 보여질 페이지번호 갯수

	
	public Member() {
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	public String getzip_num() {
		return zip_num;
	}
	public void setzip_num(String zip_num) {
		this.zip_num = zip_num;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUseyn() {
		return useyn;
	}
	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
	
	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Integer getListCount() {
		return listCount;
	}

	public void setListCount(Integer listCount) {
		this.listCount = listCount;
	}

	public Integer getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(Integer pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", zip_num=" + zip_num
				+ ", address=" + address + ", phone=" + phone + ", useyn=" + useyn + ", regdate=" + regdate + "]";
	}

}
