package com.nju.networktest.service.Impl;

import com.nju.networktest.service.NetworkService;
import com.nju.networktest.utils.TelnetConnect;

import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    TelnetConnect telnetConnect;

    @Override
    public String executeCommand(String directive,String hostName) {
        telnetClient telnetClientByName = telnetConnect.getTelnetClientByName(hostName);
        return telnetClientByName.sendCommand(directive);

    }

    //获取端口状态
    @Override
    public boolean getPortState(String portId,String address) {
        telnetClient telnetClientByIp = telnetConnect.getTelnetClientByIp(address);
        String output = telnetClientByIp.sendCommand("show int " + portId);
        //这里再斟酌下，看看返回值到底是什么
        String[] split = output.split("\n");
        if(split[0].indexOf("up")==-1)return true;
        else return false;
    }
}
