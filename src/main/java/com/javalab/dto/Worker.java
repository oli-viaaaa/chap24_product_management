package com.javalab.dto;

public class Worker {
	private String id;
	private String pwd;
	private String name;

	 //pagination field
	 private String pageNum = "1";		//페이지 번호
	 private String searchText = "";	//조회 키워드
	 private Integer listCount = 5;		//1페이지당 게시물수
	 private Integer pagePerBlock = 5;	//한 번에 보여질 페이지번호 갯수
	
	
	public Worker() {
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
		return "Worker [id=" + id + ", pwd=" + pwd + ", name=" + name + "]";
	}
}
