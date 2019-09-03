package com.ovit.jcw.utils;

import com.alibaba.fastjson.*;

public class Result
{
    private Integer code;
    private String msg;
    private Object data;
    private long hitsTotal;
    private double tookInSec;
    
    public double getTookInSec() {
        return this.tookInSec;
    }
    
    public void setTookInSec(final double tookInSec) {
        this.tookInSec = tookInSec;
    }
    
    public Result() {
        this.code = ResultCode.SUCCESS.code;
    }
    
    public Result(final ResultCode code, final String msg, final Object data) {
        this.code = code.code;
        this.msg = msg;
        this.data = data;
    }
    
    public Result(final ResultCode code, final String msg) {
        this.code = code.code;
        this.msg = msg;
    }
    
    public Result setCode(final ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public Result setCode(final Integer code) {
        this.code = code;
        return this;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public Result setMsg(final String msg) {
        this.msg = msg;
        return this;
    }
    
    public Object getData() {
        return this.data;
    }
    
    public Result setData(final Object data) {
        this.data = data;
        return this;
    }
    
    @Override
    public String toString() {
        if (null == this.code) {
            this.setCode(ResultCode.SUCCESS);
        }
        if (null == this.data) {
            this.setData("");
        }
        final String result = JSON.toJSONString((Object)this);
        return result;
    }
    
    public long getHitsTotal() {
        return this.hitsTotal;
    }
    
    public void setHitsTotal(final long hitsTotal) {
        this.hitsTotal = hitsTotal;
    }
}
