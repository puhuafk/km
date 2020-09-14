package com.stylefeng.guns.core.util;
/**
 *
 */
/**
 * @ClassName: PageInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author zhoushun
 * @date 2012-3-11 下午12:45:13
 *
 */
/**
 * 该类描述了分页记录集中的关于页的信息
 */
public class PageInfo {
    /**
     * 总共记录数
     */
    private int totalRow;	//
    /**
     * 总共页数
     */
    private int totalPage;	//
    /**
     * 当前页，默认为1
     */
    private int currentPage = 1; //
    /**
     * 页的大小(默认显示10条)
     */
    private int pageSize = 10;   //
    /**
     * 当前页的第一条记录的编号
     */
    private int startRow=2;
    private boolean hasPrevious;
    private boolean hasNext;
    private boolean bof;
    private boolean eof;
    /** 构造方法
     @param totalRow 总记录数
     @param pageSize 页的大小
     @param page 页码
     */
    public PageInfo(int totalRow, int pageSize, int page) {
        this.totalRow = totalRow;
        this.pageSize = pageSize;
        // 根据页大小和总记录数计算出总页数
        this.totalPage = countTotalPage(this.pageSize, this.totalRow);
        setCurrentPage(page);
        //获取当前页的第一条记录的编号
        this.startRow=getStartRow(this.pageSize, this.currentPage);
        //System.out.println("startRow:"+startRow);
        // 修正当前页

        init();
    }
    /**
     * 获取当前页的第一条记录的编号
     * @param pageSize(页的大小)
     * @param page（页码）
     * @return
     */
    public int getStartRow(int pageSize, int page){

        return pageSize*(page-1)+1;
    }

    public int getTotalRow() {
        return totalRow;
    }


    public int getStartRow() {
        return startRow;
    }
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }


    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }


    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }


    public void setBof(boolean bof) {
        this.bof = bof;
    }


    public void setEof(boolean eof) {
        this.eof = eof;
    }


    public int getTotalPage() {
        return totalPage;
    }
    public int getCurrentPage() {

        return this.currentPage;
    }
    // 修正计算当前页
    public void setCurrentPage(int currentPage) {
        if(currentPage>this.totalPage){
            this.currentPage=this.totalPage;
        }else if (currentPage<1){
            this.currentPage=1;
        }
        else{
            this.currentPage=currentPage;
        }
    }
    // 获取分页大小

    public int getPageSize() {
        return pageSize;
    }

    // 设置分页大小
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    // 获取当前页记录的开始索引
    public int getBeginIndex() {
        int beginIndex = (currentPage - 1) * pageSize; // 索引下标从0开始
        if(beginIndex < 0){
            return 0;
        }
        return beginIndex;
    }
    /**
     *  计算总页数
     * @param pageSize
     * @param totalRow
     * @return
     */
    public int countTotalPage(int pageSize, int totalRow) {
        int totalPage = totalRow%pageSize == 0 ? (totalRow/pageSize):(totalRow/pageSize)+1;
        return totalPage;
    }
    /**
     *  返回下一页的页码
     * @return
     */
    public int getNextPage() {

        if (currentPage + 1 >= this.totalPage) { // 如果当前页已经是最后页 则返回最大页
            return this.totalPage;
        }
        return currentPage + 1;
    }
    // 返回前一页的页码

    public int getPreviousPage() {
        if (currentPage - 1 <= 1) {

            return 1;

        } else {
            return currentPage - 1;
        }

    }
    public boolean isHasPrevious() {
        return hasPrevious;
    }
    public boolean isHasNext() {
        return hasNext;
    }
    public boolean isBof() {
        return bof;

    }
    public boolean isEof() {

        return eof;

    }
    public boolean hasNext() {

        return currentPage < this.totalPage;

    }
    public boolean hasPrevious() {

        return currentPage > 1;

    }
    public boolean isFirst() {

        return currentPage == 1;

    }
    public boolean isLast() {

        return currentPage >= this.totalPage;

    }
    // 初始化信息

    private void init() {

        this.hasNext = hasNext();

        this.hasPrevious = hasPrevious();

        this.bof = isFirst();

        this.eof = isLast();

    }
}
