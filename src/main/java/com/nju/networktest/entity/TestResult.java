package com.nju.networktest.entity;

import java.util.ArrayList;
import java.util.List;

public class TestResult {
    private List<TestResultItem> routerA;
    private List<TestResultItem> routerB;
    private List<TestResultItem> routerC;

    public TestResult(List<TestResultItem> routerA, List<TestResultItem> routerB, List<TestResultItem> routerC) {
        this.routerA = routerA;
        this.routerB = routerB;
        this.routerC = routerC;
    }

    public TestResult(TestScript testScript){
        this.routerA=new ArrayList<>();
        this.routerB=new ArrayList<>();
        this.routerC=new ArrayList<>();
        for(TestScriptItem testScriptItem:testScript.getRouterA()){
            this.routerA.add(new TestResultItem(testScriptItem));
        }
        for(TestScriptItem testScriptItem:testScript.getRouterB()){
            this.routerB.add(new TestResultItem(testScriptItem));
        }
        for(TestScriptItem testScriptItem:testScript.getRouterC()){
            this.routerC.add(new TestResultItem(testScriptItem));
        }
    }

    public TestResult() {
    }

    public List<TestResultItem> getRouterA() {
        return routerA;
    }

    public void setRouterA(List<TestResultItem> routerA) {
        this.routerA = routerA;
    }

    public List<TestResultItem> getRouterB() {
        return routerB;
    }

    public void setRouterB(List<TestResultItem> routerB) {
        this.routerB = routerB;
    }

    public List<TestResultItem> getRouterC() {
        return routerC;
    }

    public void setRouterC(List<TestResultItem> routerC) {
        this.routerC = routerC;
    }
}
