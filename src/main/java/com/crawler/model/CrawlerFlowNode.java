package com.crawler.model;

/**
 * @author xumx
 * @date 2018/10/19
 */
public class CrawlerFlowNode {

    private int nodeId;
    private String nodeDesc;
    private String nodeType;//1-action;2-others
    private String parentNodeId;
    private String nodeExcecuteCondition;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(String parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public String getNodeExcecuteCondition() {
        return nodeExcecuteCondition;
    }

    public void setNodeExcecuteCondition(String nodeExcecuteCondition) {
        this.nodeExcecuteCondition = nodeExcecuteCondition;
    }
}
