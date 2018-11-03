package com.crawler.model;

/**
 * @author xumx
 * @date 2018/10/19
 */
public class CrawlerAction extends CrawlerBaseModel{

    private int actionId;
    private String actionDesc;
    private String actionType;//1-获取html;2-点击
    private String url_addr;
    private String crawler_regex;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getUrl_addr() {
        return url_addr;
    }

    public void setUrl_addr(String url_addr) {
        this.url_addr = url_addr;
    }

    public String getCrawler_regex() {
        return crawler_regex;
    }

    public void setCrawler_regex(String crawler_regex) {
        this.crawler_regex = crawler_regex;
    }

}
