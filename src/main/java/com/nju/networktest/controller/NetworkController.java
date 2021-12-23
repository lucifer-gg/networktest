package com.nju.networktest.controller;

import com.nju.networktest.entity.vo.BasicResponse;
import com.nju.networktest.entity.vo.ResponseStatus;
import com.nju.networktest.mapper.ScriptFileMapper;
import com.nju.networktest.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class NetworkController {
    @Autowired
    NetworkService networkService;
    @Autowired
    ScriptFileMapper scriptFileMapper;


    @GetMapping("/test")
    public BasicResponse test(int id){
        return new BasicResponse(ResponseStatus.STATUS_SUCCESS,scriptFileMapper.test(id));
    }

    //交互式指令执行
    @GetMapping("/execute")
    public BasicResponse executeCommand(@RequestParam("directive")String directive,@RequestParam("hostName")String hostName){
        try{
            String s = networkService.executeCommand(directive,hostName);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,s);
        }catch (Exception e){
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/getPortState")
    public BasicResponse getPortState(@RequestParam("portId")String portId,@RequestParam("address")String address){
        try{
            boolean state = networkService.getPortState(portId,address);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,state);
        }catch (Exception e){
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

}
