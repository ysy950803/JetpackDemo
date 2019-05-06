package com.ysy.jetpackdemo2.data.entity;

public class RepoState {

    public static final int LOADING = 1;
    public static final int SUCCESS = 2;
    public static final int FAIL = 3;

    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public boolean isFinished() {
        return state == SUCCESS || state == FAIL;
    }
}
