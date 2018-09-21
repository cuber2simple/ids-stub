package org.cuber.stub.rpc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.StubConstant;
import org.cuber.stub.json.JacksonHolder;

@ApiModel("分页请求对象")
public class PageReq<T> extends Req<T>{

    private static final long serialVersionUID = -1222757821031447844L;

    @ApiModelProperty("查询页的页面大小")
    private int pageSize;

    @ApiModelProperty("查询页的页面号")
    private int pageNum;

    public PageReq() {
        super();
        pageSize = StubConstant.DEFAULT_PAGE_SIZE ;
        pageNum = StubConstant.DEFAULT_PAGE_NUM ;
    }

    public PageReq(T req) {
        super(req);
        pageSize = StubConstant.DEFAULT_PAGE_SIZE ;
        pageNum = StubConstant.DEFAULT_PAGE_NUM ;
    }

    public PageReq(int pageSize, int pageNum) {
        super();
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public PageReq(T req, int pageSize, int pageNum) {
        super(req);
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
