package com.nju.networktest.entity.vo;

import com.nju.networktest.entity.ConsoleOutput;
import com.nju.networktest.entity.ScriptFile;

public class ExecuteScriptFileVO {
    private ScriptFile scriptFile;
    private ConsoleOutput consoleOutput;

    public ExecuteScriptFileVO(ScriptFile scriptFile, ConsoleOutput consoleOutput) {
        this.scriptFile = scriptFile;
        this.consoleOutput = consoleOutput;
    }

    public ExecuteScriptFileVO() {
    }

    public ScriptFile getScriptFile() {
        return scriptFile;
    }

    public void setScriptFile(ScriptFile scriptFile) {
        this.scriptFile = scriptFile;
    }

    public ConsoleOutput getConsoleOutput() {
        return consoleOutput;
    }

    public void setConsoleOutput(ConsoleOutput consoleOutput) {
        this.consoleOutput = consoleOutput;
    }
}
