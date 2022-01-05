package com.nju.networktest.service;

import com.nju.networktest.entity.PortState;
import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface NetworkService {


    String executeCommand(String directive,String hostName);

    PortState getPortState(String portId, String hostName);

    boolean reloadAll();

    boolean reload(String hostName);

    boolean establishConnect();

    void flashAll();

    boolean upDatePortAddress(String hostIp,String portId,String newPortIp,String newMask);

    List<PortState> getRouterState(String hostName);

}
