package com.nju.networktest.entity.po;

public class ScriptFilePO {
    private Integer id;
    private String fileName;
    private String uselessInfo;

    public Integer getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUselessInfo() {
        return uselessInfo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUselessInfo(String uselessInfo) {
        this.uselessInfo = uselessInfo;
    }

    public ScriptFilePO() {
    }

    public ScriptFilePO(String fileName, String uselessInfo) {
        this.fileName = fileName;
        this.uselessInfo = uselessInfo;
    }
}
