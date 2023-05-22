package com.javalab.util;

/**
 * 페이징 네비게이터 함수
 * @author stoneis.pe.kr
 * @since 2013.07.10
 */
public class PageNavigator {
	
	/**
	 * 페이징 네비게이터를 만들어주는 함수
	 * @param totalCount	- 총수
	 * @param listCount		- 노출될 목록 게시물 수
	 * @param pagePerBlock	- 노출될 블록 수 [ 1 2 3 4 5 ] 5개
	 * @param pageNum		- 페이지 번호
	 * @param searchType	- 검색 항목
	 * @param searchText	- 검색어
	 * @return
	 */
	public String getPageNavigator(int totalCount, int listCount, int pagePerBlock,
			int pageNum, String searchText) {
		
		StringBuffer sb = new StringBuffer();	
		
		if(totalCount > 0) {	//총 레코드 수가 하나라도 있어야 페이징
			//총 페이지수
			int totalNumOfPage = (totalCount % listCount == 0) ? totalCount / listCount :	totalCount / listCount + 1;
			
			//페이지 번호 그룹
			int totalNumOfBlock = (totalNumOfPage % pagePerBlock == 0) ? totalNumOfPage / pagePerBlock : totalNumOfPage / pagePerBlock + 1;
			
			//요청된 페이지가 몇 번째 페이지 그룹에 있는지 확인(디테일 뷰로 갔다 돌아왔을 때 기억하고 있어야.)
			int currentBlock = (pageNum % pagePerBlock == 0) ? 
					pageNum / pagePerBlock :
					pageNum / pagePerBlock + 1;
			
			//시작페이지와 끝페이지(한 페이지 블럭 내에서)
			int startPage = (currentBlock - 1) * pagePerBlock + 1;
			int endPage = startPage + pagePerBlock - 1;
			
			if(endPage > totalNumOfPage)
				endPage = totalNumOfPage;
			
			boolean isNext = false;
			boolean isPrev = false;
			
			//현재 페이지가 토털 블럭 보다 작을 때만 >> 보여줌(총 페이지 블럭이 3인데 내가 현재 있는 페이지 블럭이 3이면 >> 안나와야)
			if(currentBlock < totalNumOfBlock)
				isNext = true;
			
			if(currentBlock > 1)
				isPrev = true;
			if(totalNumOfBlock == 1){
				isNext = false;
				isPrev = false;
			}
			
			if(pageNum > 1){	// 쿼리스트링에서 & = &amp; 
				sb.append("<a href=\"").append("productList.do?pageNum=1&amp;searchText="+searchText);
				sb.append("\" title=\"<<\"><<</a>&nbsp;");
			}
			if (isPrev) {	//<< 가 있으면
				int goPrevPage = startPage - pagePerBlock;			
				sb.append("&nbsp;&nbsp;<a href=\"").append("productList.do?pageNum="+goPrevPage+"&amp;searchText="+searchText);
				sb.append("\" title=\"<\"><</a>");
			} else {
				
			}
			for (int i = startPage; i <= endPage; i++) {
				if (i == pageNum) {
					sb.append("<a href=\"#\"><strong>").append(i).append("</strong></a>&nbsp;&nbsp;");
				} else {
					sb.append("<a href=\"").append("productList.do?pageNum="+i+"&amp;searchText="+searchText);
					sb.append("\" title=\""+i+"\">").append(i).append("</a>&nbsp;&nbsp;");
				}
			}
			if (isNext) {
				int goNextPage = startPage + pagePerBlock;
	
				sb.append("<a href=\"").append("productList.do?pageNum="+goNextPage+"&amp;searchText="+searchText);
				sb.append("\" title=\">\">></a>");
			} else {
				
			}
			if(totalNumOfPage > pageNum){
				sb.append("&nbsp;&nbsp;<a href=\"").append("productList.do?pageNum="+totalNumOfPage+"&amp;searchText="+searchText);
				sb.append("\" title=\">>\">>></a>");
			}
			
		}
		
		return sb.toString();
	}
}
