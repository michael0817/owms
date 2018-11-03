package com.bootdo.order.enums;

public enum ModuleType {
    ORDER(1), MODULE(2), EXPRESS(3), STOCK(4);
    // 成员变量
    private Integer index;
    // 构造方法
    private ModuleType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
