package com.nju.networktest.service;

import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;

public interface NetworkService {


    public String executeCommand(String directive);

    public String getPortState(String portId);

}
