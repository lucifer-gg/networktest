package com.nju.networktest.entity;

import java.util.List;

public class CommandList {
    private List<String> routerA;
    private List<String> routerB;
    private List<String> routerC;

    public void setRouterA(List<String> routerA) {
        this.routerA = routerA;
    }

    public void setRouterB(List<String> routerB) {
        this.routerB = routerB;
    }

    public void setRouterC(List<String> routerC) {
        this.routerC = routerC;
    }

    public List<String> getRouterA() {
        return routerA;
    }

    public List<String> getRouterB() {
        return routerB;
    }

    public List<String> getRouterC() {
        return routerC;
    }

    public CommandList(List<String> routerA, List<String> routerB, List<String> routerC) {
        this.routerA = routerA;
        this.routerB = routerB;
        this.routerC = routerC;
    }

    public CommandList() {
    }
}
