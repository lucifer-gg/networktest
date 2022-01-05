package com.nju.networktest.entity;

public class TestScriptItem {
    private String command;
    private String type;
    private String expected;

    public TestScriptItem() {
    }

    public TestScriptItem(String command, String type, String expected) {

        this.command = command;
        this.type = type;
        this.expected = expected;
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
}
