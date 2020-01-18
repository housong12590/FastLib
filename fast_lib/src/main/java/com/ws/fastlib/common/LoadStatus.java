package com.ws.fastlib.common;

public enum LoadStatus {
    /**
     * 加载中
     */
    LOADING(0),
    /**
     * 刷新
     */
    REFRESH(1),
    /**
     * 加载更多
     */
    LOAD_MORE(2);

    private int value;

    LoadStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
