

package com.sinry.utils;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page<T> implements Serializable {
    @ApiModelProperty("当前页")
    private int pageNo;
    @ApiModelProperty("总页数")
    private int totalPage;
    @ApiModelProperty("每页最大条数")
    private int rowCntPerPage;
    @ApiModelProperty("总记录数")
    private long totalRowCount;
    @ApiModelProperty("响应数据集")
    private List<T> list;
    @ApiModelProperty("排序")
    private String order;
    @ApiModelProperty("条件")
    private T condition;

    public Page() {
        this.pageNo = 1;
        this.totalPage = 1;
        this.rowCntPerPage = 10;
        this.totalRowCount = 0L;
    }

    public Page(PageInfo pageInfo) {
        this.pageNo = pageInfo.getPageNum();
        this.totalPage = pageInfo.getPages();
        this.rowCntPerPage = pageInfo.getPageSize();
        this.totalRowCount = pageInfo.getTotal();
        this.list = pageInfo.getList();
    }

    public Page(com.github.pagehelper.Page page) {
        this.pageNo = page.getPageNum();
        this.totalPage = page.getPages();
        this.rowCntPerPage = page.getPageSize();
        this.totalRowCount = page.getTotal();
        this.list = page.getResult();
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getRowCntPerPage() {
        return this.rowCntPerPage;
    }

    public long getTotalRowCount() {
        return this.totalRowCount;
    }

    public List<T> getList() {
        return this.list;
    }

    public String getOrder() {
        return this.order;
    }

    public T getCondition() {
        return this.condition;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setRowCntPerPage(int rowCntPerPage) {
        this.rowCntPerPage = rowCntPerPage;
    }

    public void setTotalRowCount(long totalRowCount) {
        this.totalRowCount = totalRowCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

}
