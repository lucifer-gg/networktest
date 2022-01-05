package com.nju.networktest.service.Impl;

import com.nju.networktest.entity.PortState;
import com.nju.networktest.service.NetworkService;
import com.nju.networktest.utils.TelnetConnect;

import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public PortState getPortState(String portId,String hostName) {
        telnetClient telnet = new telnetClient("VT220","#");
        if(telnet.login(telnetConnect.getIpByName(hostName), 23, "cisco")){
            String output = telnet.sendCommand("show ip int " + portId);
            telnet.distinct();
            //这里再斟酌下，看看返回值到底是什么
            //可以截取完整的语句，后面改
            String[] split = output.split("\n");
            //第0行还是第一行
            if(split[1].split(",")[0].contains("down")){
                return new PortState(hostName,portId,"unassigned","down");
            }else {
                String portIpAddress=split[2].replace("Internet address is ","").trim();
                return new PortState(hostName,portId,portIpAddress,"up");
            }
        }else{
            System.out.println("get port state 建立链接失败");
//            telnetClient telnetClientByIp = telnetConnect.getTelnetClientByName(hostName);
//            String output = telnetClientByIp.sendCommand("show ip int " + portId);
//            //这里再斟酌下，看看返回值到底是什么
//            //可以截取完整的语句，后面改
//            String[] split = output.split("\n");
//            //第0行还是第一行
//            if(split[1].split(",")[0].contains("down")){
//                return new PortState(hostName,portId,"unassigned","down");
//            }else {
//                String portIpAddress=split[2].replace("Internet address is ","").trim();
//                return new PortState(hostName,portId,portIpAddress,"up");
//            }
            return null;
        }



    }

    @Override
    public boolean upDatePortAddress(String hostIp,String portId,String newPortIp,String newMask){
        telnetClient telnet = new telnetClient("VT220","#");        //Windows,用VT220,否则会乱码
        StringBuilder sb=new StringBuilder();
        if(telnet.login(hostIp, 23, "cisco")){
            System.out.println("login success");
            sb.append(telnet.sendCommand("conf t"));
            String tmpCommand="int "+portId;
            sb.append(telnet.sendCommand(tmpCommand));
            tmpCommand="ip address "+newPortIp+" "+newMask;
            sb.append(telnet.sendCommand(tmpCommand));
            sb.append(telnet.sendCommand("no shut"));
            sb.append(telnet.sendCommand("exit"));
            sb.append(telnet.sendCommand("exit"));
            telnet.distinct();
            return true;
        }else return false;
//        StringBuilder sb=new StringBuilder();
//        telnetClient telnet = telnetConnect.getTelnetClientByIp(hostIp);
//        sb.append(telnet.sendCommand("conf t"));
//        String tmpCommand="int "+portId;
//        sb.append(telnet.sendCommand(tmpCommand));
//        tmpCommand="ip address "+newPortIp+" "+newMask;
//        sb.append(telnet.sendCommand(tmpCommand));
//        sb.append(telnet.sendCommand("no shut"));
//        sb.append(telnet.sendCommand("exit"));
//        sb.append(telnet.sendCommand("exit"));

    }

    @Override
    public List<PortState> getRouterState(String hostName) {
        List<String> portList=new ArrayList<>(Arrays.asList("f0/0","f0/1","s0/0/0","s0/0/1"));
        List<PortState> res=new ArrayList<>();
        for(String portId:portList){
            PortState portState = getPortState(portId, hostName);
            res.add(portState);
        }
        return res;
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
        telnetClient telnetA = new telnetClient("VT220","#");
        telnetClient telnetB = new telnetClient("VT220","#");
        telnetClient telnetC = new telnetClient("VT220","#");
        if(telnetA.login(telnetConnect.getIpByName("routerA"), 23, "cisco")){
            telnetA.sendCommand("copy startup-config running-config","?");
            telnetA.sendCommand("\n");
        }

        if(telnetB.login(telnetConnect.getIpByName("routerB"), 23, "cisco")){
            telnetB.sendCommand("copy startup-config running-config","?");
            telnetB.sendCommand("\n");

        }

        if(telnetC.login(telnetConnect.getIpByName("routerC"), 23, "cisco")){

            telnetC.sendCommand("copy startup-config running-config","?");
            telnetC.sendCommand("\n");
        }
        telnetA.distinct();
        telnetB.distinct();
        telnetC.distinct();

//        telnetConnect.getTelnetClientByName("routerA").sendCommand("copy startup-config running-config","?");
//        String s1= telnetConnect.getTelnetClientByName("routerA").sendCommand("\n");
//        System.out.println(s1);
//        telnetConnect.getTelnetClientByName("routerB").sendCommand("copy startup-config running-config","?");
//        String s2= telnetConnect.getTelnetClientByName("routerB").sendCommand("\n");
//        System.out.println(s2);
//        telnetConnect.getTelnetClientByName("routerC").sendCommand("copy startup-config running-config","?");
//        String s3= telnetConnect.getTelnetClientByName("routerC").sendCommand("\n");
//        System.out.println(s3);
    }
}
