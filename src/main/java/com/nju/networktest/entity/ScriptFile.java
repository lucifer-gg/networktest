package com.nju.networktest.entity;

import com.nju.networktest.entity.po.ScriptFilePO;

import java.util.List;


public class ScriptFile {
    private String fileName;
    private String uselessInfo;
    private CommandList command;

    public ScriptFile() {
    }

    public ScriptFile(String fileName, String uselessInfo, CommandList command) {
        this.fileName = fileName;
        this.uselessInfo = uselessInfo;
        this.command = command;
    }
    public ScriptFile(ScriptFilePO scriptFilePO,CommandList commandList){
        this.fileName=scriptFilePO.getFileName();
        this.uselessInfo=scriptFilePO.getUselessInfo();
        this.command=commandList;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUselessInfo() {
        return uselessInfo;
    }

    public CommandList getCommand() {
        return command;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUselessInfo(String uselessInfo) {
        this.uselessInfo = uselessInfo;
    }

    public void setCommand(CommandList command) {
        this.command = command;
    }


}


