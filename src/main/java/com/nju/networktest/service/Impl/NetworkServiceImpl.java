package com.nju.networktest.service.Impl;

import com.nju.networktest.service.NetworkService;
import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NetworkServiceImpl implements NetworkService {

    @Autowired
    telnetClient telnetClient;

    @Override
    public String executeCommand(String directive) {
        return telnetClient.sendCommand(directive);
    }

    //获取端口状态
    @Override
    public String getPortState(String portId) {
        String command="查询命令";

        String res="up";
        return res;
    }
}
