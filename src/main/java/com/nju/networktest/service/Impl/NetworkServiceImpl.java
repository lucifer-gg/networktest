package com.nju.networktest.service.Impl;

import com.nju.networktest.service.NetworkService;
import com.nju.networktest.utils.TelnetConnect;

import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    TelnetConnect telnetConnect;

    @Override
    public String executeCommand(String directive,String hostName) {
        telnetClient telnetClientByName = telnetConnect.getTelnetClientByName(hostName);
        String ss = telnetClientByName.sendCommand(directive);
        System.out.println("---编码前");
        System.out.println(ss);
        System.out.println(ss.indexOf("!"));
        System.out.println("--编码后--");
        try {
            ss = new String(ss.getBytes("ISO-8859-1"),"GBK");
            System.out.println(ss);
            System.out.println("----");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ss;

    }

    //获取端口状态
    @Override
    public boolean getPortState(String portId,String address) {
        telnetClient telnetClientByIp = telnetConnect.getTelnetClientByIp(address);
        String output = telnetClientByIp.sendCommand("show int " + portId);
        //这里再斟酌下，看看返回值到底是什么
        //可以截取完整的语句，后面改
        String[] split = output.split("\n");
        if(split[0].indexOf("up")==-1)return true;
        else return false;
    }

    @Override
    public boolean reloadAll() {
        return reload("routerA")&&reload("routerB")&&reload("routerC");
    }


    @Override
    public boolean establishConnect() {
        return telnetConnect.establishConnect();
    }


    @Override
    public boolean reload(String hostName){
        telnetClient telnetClientByName = telnetConnect.getTelnetClientByName(hostName);
        String reload = telnetClientByName.sendCommand("reload", "]");
        if(reload.contains("System configuration has been modified. Save? [yes/no]")){
            telnetClientByName.write("no");
            telnetClientByName.write("\n");
        }else if(reload.contains("Proceed with reload? [confirm]")){
            telnetClientByName.write("\n");
        }else{
            return false;
        }
        //发完reload命令后断开连接池的连接
        telnetClientByName.distinct();
        if("routerA".equals(hostName)){
            telnetConnect.telnetClientForRouterA=new telnetClient("VT220","#");
        }else if("routerB".equals(hostName)){
            telnetConnect.telnetClientForRouterB=new telnetClient("VT220","#");
        }else {
            telnetConnect.telnetClientForRouterC=new telnetClient("VT220","#");
        }

        return true;

    }

    @Override
    public void flashAll(){
        telnetConnect.getTelnetClientByName("routerA").sendCommand("copy startup-config running-config","?");
        String s1= telnetConnect.getTelnetClientByName("routerA").sendCommand("\n");
        System.out.println(s1);
        telnetConnect.getTelnetClientByName("routerB").sendCommand("copy startup-config running-config","?");
        String s2= telnetConnect.getTelnetClientByName("routerB").sendCommand("\n");
        System.out.println(s2);
        telnetConnect.getTelnetClientByName("routerC").sendCommand("copy startup-config running-config","?");
        String s3= telnetConnect.getTelnetClientByName("routerC").sendCommand("\n");
        System.out.println(s3);
    }
}
