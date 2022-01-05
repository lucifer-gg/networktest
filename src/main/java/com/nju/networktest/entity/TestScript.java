package com.nju.networktest.entity;

import java.util.List;

public class TestScript {
    private List<TestScriptItem> routerA;
    private List<TestScriptItem> routerB;
    private List<TestScriptItem> routerC;

    public List<TestScriptItem> getRouterA() {
        return routerA;
    }

    public void setRouterA(List<TestScriptItem> routerA) {
        this.routerA = routerA;
    }

    public List<TestScriptItem> getRouterB() {
        return routerB;
    }

    public void setRouterB(List<TestScriptItem> routerB) {
        this.routerB = routerB;
    }

    public List<TestScriptItem> getRouterC() {
        return routerC;
    }

    public void setRouterC(List<TestScriptItem> routerC) {
        this.routerC = routerC;
    }

    public TestScript(List<TestScriptItem> routerA, List<TestScriptItem> routerB, List<TestScriptItem> routerC) {
        this.routerA = routerA;
        this.routerB = routerB;
        this.routerC = routerC;
    }

    public TestScript() {
    }
}
