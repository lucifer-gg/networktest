package com.nju.networktest.utils;
import org.springframework.stereotype.Component;

@Component
//这个类用来维持三个telnet连接
public class TelnetConnect {
    public String ipA="192.168.80.1";
    public String ipB="192.168.80.2";
    public String ipC="192.168.80.3";

    public telnetClient telnetClientForRouterA;
    public telnetClient telnetClientForRouterB;
    public telnetClient telnetClientForRouterC;

    public String getIpByName(String hostName){
        if("routerA".equals(hostName))return ipA;
        if("routerB".equals(hostName))return ipB;
        if("routerC".equals(hostName))return ipC;
        return null;
    }

    public TelnetConnect(){
        //创建spring程序的时候只创建对象，不连接
        this.telnetClientForRouterA=new telnetClient("VT220","#");

        this.telnetClientForRouterB=new telnetClient("VT220","#");

        this.telnetClientForRouterC=new telnetClient("VT220","#");

//        //启动后就向三台服务器发送保存状态的请求

    }

    public telnetClient getTelnetClientByIp(String ip){


        if(ip.equals(ipA)){
            if(!telnetClientForRouterA.isConnected()){
                reConnectToRouterA();
            }
            return telnetClientForRouterA;
        }else if(ip.equals(ipB)){
            if(!telnetClientForRouterB.isConnected()){
                reConnectToRouterB();
            }
            return telnetClientForRouterB;
        }else {
            if(!telnetClientForRouterC.isConnected()){
                reConnectToRouterC();
            }
            return telnetClientForRouterC;
        }
    }

    public telnetClient getTelnetClientByName(String routerName){
        if("routerA".equals(routerName)){
            if(!telnetClientForRouterA.isConnected()){
                reConnectToRouterA();
            }
            return telnetClientForRouterA;
        }else if("routerB".equals(routerName)){
            if(!telnetClientForRouterB.isConnected()){
                reConnectToRouterB();
            }
            return telnetClientForRouterB;
        }else {
            if(!telnetClientForRouterC.isConnected()){
                reConnectToRouterC();
            }
            return telnetClientForRouterC;
        }
    }

    public void closeAllTelnetConnect(){
        telnetClientForRouterA.distinct();
        telnetClientForRouterB.distinct();
        telnetClientForRouterC.distinct();
    }

    public boolean establishConnect(){
        if (this.telnetClientForRouterA.login(ipA,23,"cisco") && this.telnetClientForRouterB.login(ipB, 23, "cisco") && this.telnetClientForRouterC.login(ipC, 23, "cisco")){
//        if(this.telnetClientForRouterA.login(ipA, 23, "cisco")){
            return true;
        }
        return false;
    }

    public boolean reConnectToRouterA(){
        if (!this.telnetClientForRouterA.isConnected()){
            this.telnetClientForRouterA=new telnetClient("VT220","#");
            return this.telnetClientForRouterA.login(ipA, 23, "cisco");
        }
        return true;
    }

    public boolean reConnectToRouterB(){
        if (!this.telnetClientForRouterB.isConnected()){
            this.telnetClientForRouterB=new telnetClient("VT220","#");
            return this.telnetClientForRouterB.login(ipB, 23, "cisco");
        }
        return true;
    }

    public boolean reConnectToRouterC(){
        if (!this.telnetClientForRouterC.isConnected()){
            this.telnetClientForRouterC=new telnetClient("VT220","#");
            return this.telnetClientForRouterC.login(ipC, 23, "cisco");
        }
        return true;
    }

    public boolean reConnectToAll(){
        return reConnectToRouterA()&&reConnectToRouterB()&&reConnectToRouterC();
    }

}
