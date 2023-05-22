package com.javalab.dto;

import java.sql.Date;

public class Product {

	//데이터베이스 필드
	 private Integer code;				//상품코드primary key	자동 매핑을 위해서(int -> Integer) 	
	 private String name;				//상품명
	 private Integer kind;				//상품종류
	 private Integer cost_price;		//원가
	 private Integer list_price;		//정가
	 private Integer sales_margin;		//마진
	 private String content;			//상품내용
	 private String image;				//상품이미지명
	 private String useyn;				//사용유무
	 private String bestyn;				//인기상품유무
	 private Date regdate;				//상품등록일
	 
	 //pagination field
	 private String pageNum = "1";		//페이지 번호
	 private String searchText = "";	//조회 키워드
	 private Integer listCount = 10;		//1페이지당 게시물수
	 private Integer pagePerBlock = 5;	//한 번에 보여질 페이지번호 갯수
	 
	 public Product() {
	 }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getCost_price() {
		return cost_price;
	}

	public void setCost_price(Integer cost_price) {
		this.cost_price = cost_price;
	}

	public Integer getList_price() {
		return list_price;
	}

	public void setList_price(Integer list_price) {
		this.list_price = list_price;
	}

	public Integer getSales_margin() {
		return sales_margin;
	}

	public void setSales_margin(Integer sales_margin) {
		this.sales_margin = sales_margin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUseyn() {
		return useyn;
	}

	public void setUseyn(String useyn) {
		this.useyn = useyn;
	}

	public String getBestyn() {
		return bestyn;
	}

	public void setBestyn(String bestyn) {
		this.bestyn = bestyn;
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
		return "Product [code=" + code + ", name=" + name + ", kind=" + kind + ", cost_price=" + cost_price
				+ ", list_price=" + list_price + ", sales_margin=" + sales_margin + ", content=" + content + ", image="
				+ image + ", useyn=" + useyn + ", bestyn=" + bestyn + ", regdate=" + regdate + ", pageNum=" + pageNum
				+ ", searchText=" + searchText + ", listCount=" + listCount + ", pagePerBlock=" + pagePerBlock + "]";
	}

	  
}
