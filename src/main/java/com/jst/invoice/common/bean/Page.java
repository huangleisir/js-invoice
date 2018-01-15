package com.jst.invoice.common.bean;

import java.io.Serializable;

/**
 * 
 * 
 * @Package: com.jst.message.bean  
 * @ClassName: Page 
 * @Description: 分页bean
 *
 * @author: lixin 
 * @date: 2016年12月14日 下午9:15:13 
 * @version V1.0
 */
public class Page
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public static final int TOTAL_PAGE_DEFAULT = 0;
  public static final int TOTAL_RECORD_DEFAULT = 0;
  public static final int START_REC_DEFAULT = 0;
  public static final int END_REC_DEFAULT = 10;
  public static final int CURRENT_PAGE_DEFAULT = 1;
  public static final int PAGE_SIZE_DEFAULT = 15;
  private int totalRecord;
  private int totalPage;
  private int startRec;
  private int endRec;
  private int currentPage;
  private int pageSize;
  public static Page page = new Page();

  public Page()
  {
    this.totalPage = 0;
    this.totalRecord = 0;
    this.startRec = 0;
    this.endRec = 10;
    this.currentPage = 1;
    this.pageSize = 15;
  }

  public void setCurrentPage(int currentPage)
  {
    if ((currentPage > this.totalPage) && (this.totalPage != 0))
      this.currentPage = this.totalPage;
    else if (currentPage < 1)
      this.currentPage = 1;
    else
      this.currentPage = currentPage;
  }

  public int getCurrentPage()
  {
    return this.currentPage;
  }

  public int getPageSize() {
    return this.pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public void setStartRec(int startRec) {
    this.startRec = startRec;
  }

  public void setEndRec(int endRec) {
    this.endRec = endRec;
  }

  public void setTotalRecord(int totalRecord) {
    this.totalRecord = totalRecord;
  }

  public int getTotalRecord() {
    return this.totalRecord;
  }

  public void setTotalPage() {
    if (this.totalRecord % this.pageSize != 0)
      this.totalPage = (this.totalRecord / this.pageSize + 1);
    else
      this.totalPage = (this.totalRecord / this.pageSize);
  }

  public int getTotalPage()
  {
    return this.totalPage;
  }

  public void setStartRec() {
    if (this.totalPage == 0)
      this.startRec = 0;
    else
      this.startRec = ((this.currentPage - 1) * this.pageSize);
  }

  public int getStartRec()
  {
    return this.startRec;
  }

  public void setEndRec() {
    if (this.totalPage <= 1)
      this.endRec = this.totalRecord;
    else if (this.currentPage == this.totalPage)
      this.endRec = this.totalRecord;
    else
      this.endRec = (this.startRec + this.pageSize);
  }

  public int getEndRec()
  {
    return this.endRec;
  }

  public static Page getPage(int currentPage, int totalRecord, int pageSize)
  {
    page.setTotalRecord(totalRecord);

    page.setPageSize(pageSize);

    page.setTotalPage();

    page.setCurrentPage(currentPage);

    page.setStartRec();

    page.setEndRec();

    return page;
  }

  public static void main(String[] args) {
    Page page = getPage(2, 11, 3);
    int startRec = page.getStartRec();
    int endRec = page.getEndRec();
    int currentPage = page.getCurrentPage();
    int pageSize = page.getPageSize();
    int totalPage = page.getTotalPage();
    int totalRecord = page.getTotalRecord();
    System.out.println("startRec=" + startRec);
    System.out.println("endRec=" + endRec);
    System.out.println("totalPage=" + totalPage);
    System.out.println("currentPage=" + currentPage);
    System.out.println("totalRecord=" + totalRecord);
    System.out.println("pageSize=" + pageSize);
  }
}
