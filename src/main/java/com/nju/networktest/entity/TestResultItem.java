package com.nju.networktest.entity;

public class TestResultItem {
    private String command;
    private String type;
    private String expected;
    private String output;
    private boolean flag;

    public TestResultItem() {
    }

    public TestResultItem(TestScriptItem testScriptItem){
        this.command=testScriptItem.getCommand();
        this.expected=testScriptItem.getExpected();
        this.type=testScriptItem.getType();
    }

    public TestResultItem(String command, String type, String expected, String output, boolean flag) {
        this.command = command;
        this.type = type;
        this.expected = expected;
        this.output = output;
        this.flag = flag;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
