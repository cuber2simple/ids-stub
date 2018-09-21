package org.cuber.stub.rpc;

import com.github.pagehelper.Page;
import org.cuber.stub.json.JacksonHolder;

import java.util.List;

public class PageResp<T> extends Resp<List<T>>{

    private static final long serialVersionUID = -4025124517796318919L;

    private int pageNum;
    private int pageSize;
    private long total;
    private int pages;

    public PageResp() {
    }

    public PageResp(Page page) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.pages = page.getPages();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
