package com.nju.networktest.service.Impl;

import com.nju.networktest.service.NetworkService;
import com.nju.networktest.utils.TelnetConnect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    TelnetConnect telnetConnect;

    @Override
    public String executeCommand(String directive) {

        return "";
    }

    //获取端口状态
    @Override
    public String getPortState(String portId) {
        String res="up";
        return res;
    }
}
