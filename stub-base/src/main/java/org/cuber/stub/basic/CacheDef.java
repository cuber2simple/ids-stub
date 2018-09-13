package org.cuber.stub.basic;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.vo.StubConfVO;

import java.time.LocalDateTime;

@ApiModel("缓存对象")
public class CacheDef<T> extends StubConfVO {

    private static final long serialVersionUID = 6105163070414008562L;

    @ApiModelProperty("缓存名称")
    private String cacheName;

    @ApiModelProperty("所属服务名")
    private String appName;

    @ApiModelProperty("ZK更新的cache地址")
    private String cacheZkPath;

    @ApiModelProperty("缓存在redis的key值")
    private String cacheRedisKey;

    @ApiModelProperty("缓存的对象名称")
    private Class<T> cacheInsClass;

    @ApiModelProperty("是否全局缓存")
    private boolean global;

    @ApiModelProperty("是否定期刷新")
    private boolean durable;

    @ApiModelProperty("定期刷新时间")
    private int durationOfMinutes;

    @ApiModelProperty("上次刷新时间")
    private LocalDateTime lastLoadDatetime;


    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCacheZkPath() {
        return cacheZkPath;
    }

    public void setCacheZkPath(String cacheZkPath) {
        this.cacheZkPath = cacheZkPath;
    }

    public String getCacheRedisKey() {
        return cacheRedisKey;
    }

    public void setCacheRedisKey(String cacheRedisKey) {
        this.cacheRedisKey = cacheRedisKey;
    }

    public Class<T> getCacheInsClass() {
        return cacheInsClass;
    }

    public void setCacheInsClass(Class<T> cacheInsClass) {
        this.cacheInsClass = cacheInsClass;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public int getDurationOfMinutes() {
        return durationOfMinutes;
    }

    public void setDurationOfMinutes(int durationOfMinutes) {
        this.durationOfMinutes = durationOfMinutes;
    }

    public LocalDateTime getLastLoadDatetime() {
        return lastLoadDatetime;
    }

    public void setLastLoadDatetime(LocalDateTime lastLoadDatetime) {
        this.lastLoadDatetime = lastLoadDatetime;
    }

    @Override
    public String toString() {
        return GsonHolder.toJson(this);
    }
}