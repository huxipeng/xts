package com.mogo.xts.enums;

/**
 * @author Xipeng
 **/
public enum ActionEnum {

    HEART_BEAT("HEART_BEAT", "心跳包"),
    TEST("TEST", "测试"),
    ;

    private String action;

    private String desc;

    ActionEnum(String action, String desc) {
        this.action = action;
        this.desc = desc;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
