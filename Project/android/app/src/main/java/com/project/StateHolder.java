package com.project;

public class StateHolder {
    private String state;
    public String getState() {return state;}
    public void setState(String _state) {this.state = _state;}

    private static final StateHolder holder = new StateHolder();
    public static StateHolder getInstance() {return holder;}
}
