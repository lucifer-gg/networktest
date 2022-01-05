package com.nju.networktest.entity;

public class PortState {
    private String host;
    private String port;
    private String ip;
    private String status;

    public PortState(String host, String port, String ip, String status) {
        this.host = host;
        this.port = port;
        this.ip = ip;
        this.status = status;
    }

    public PortState() {
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public String getStatus() {
        return status;
    }
}
