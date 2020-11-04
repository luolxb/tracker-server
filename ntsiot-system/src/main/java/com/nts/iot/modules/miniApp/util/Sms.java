package com.nts.iot.modules.miniApp.util;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by puras on 16/10/25.
 */
public class Sms {
    private String url;
    private String signature;
    private String account;
    private String pswd;
    private List<String> mobiles;
    private String msg;
    private Integer status;
    private String result;

    @Override
    public String toString() {
        return "Sms{" +
                "url='" + url + '\'' +
                ", signature='" + signature + '\'' +
                ", account='" + account + '\'' +
                ", pswd='" + pswd + '\'' +
                ", mobiles=" + mobiles +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                ", result='" + result + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void addMobile(String mobile) {
        if (null == mobiles) mobiles = new ArrayList<>();
        mobiles.add(mobile);
    }

    public String getStrMobile() {
        if (null == mobiles) return "";
        return mobiles.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    public String getFullMsg() {
        return (null == signature ? "" : signature) + (null == msg ? "" : msg);
    }
}
