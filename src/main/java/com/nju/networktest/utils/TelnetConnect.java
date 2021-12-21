package com.nju.networktest.utils;
import org.springframework.stereotype.Component;

@Component
//这个类用来维持三个telnet连接
public class TelnetConnect {
    String ipA="192.168.0.1";
    String ipB="192.168.0.2";
    String ipC="192.168.0.3";

    telnetClient telnetClientForRouterA;
    telnetClient telnetClientForRouterB;
    telnetClient telnetClientForRouterC;

    public TelnetConnect(){
        this.telnetClientForRouterA=new telnetClient("VT220","#");
        this.telnetClientForRouterA.login(ipA, 23, "cisco");
        this.telnetClientForRouterB=new telnetClient("VT220","#");
        this.telnetClientForRouterB.login(ipB, 23, "cisco");
        this.telnetClientForRouterC=new telnetClient("VT220","#");
        this.telnetClientForRouterC.login(ipC, 23, "cisco");
        //启动后就向三台服务器发送保存状态的请求

    }

    public telnetClient getTelnetClientByIp(String ip){
        if(ip.equals(ipA)){
            return telnetClientForRouterA;
        }else if(ip.equals(ipB)){
            return telnetClientForRouterB;
        }else {
            return telnetClientForRouterC;
        }
    }

    public telnetClient getTelnetClientByName(String routerName){
        if("RouterA".equals(routerName)){
            return telnetClientForRouterA;
        }else if("RouterB".equals(routerName)){
            return telnetClientForRouterB;
        }else {
            return telnetClientForRouterC;
        }
    }

    public void closeAllTelnetConnect(){
        telnetClientForRouterA.distinct();
        telnetClientForRouterB.distinct();
        telnetClientForRouterC.distinct();
    }

}
