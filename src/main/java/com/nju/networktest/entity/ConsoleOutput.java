package com.nju.networktest.entity;

public class ConsoleOutput {
    //路由器A执行脚本后的输出
    private String routerA;
    private String routerB;
    private String routerC;

    public ConsoleOutput(String routerA, String routerB, String routerC) {
        this.routerA = routerA;
        this.routerB = routerB;
        this.routerC = routerC;
    }

    public ConsoleOutput() {
    }

    public String getRouterA() {
        return routerA;
    }

    public void setRouterA(String routerA) {
        this.routerA = routerA;
    }

    public String getRouterB() {
        return routerB;
    }

    public void setRouterB(String routerB) {
        this.routerB = routerB;
    }

    public String getRouterC() {
        return routerC;
    }

    public void setRouterC(String routerC) {
        this.routerC = routerC;
    }
}
