package com.nju.networktest.service;

import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;

public interface NetworkService {


    String executeCommand(String directive,String hostName);

    boolean getPortState(String portId,String address);

    boolean reloadAll();

    boolean reload(String hostName);

    boolean establishConnect();

}
