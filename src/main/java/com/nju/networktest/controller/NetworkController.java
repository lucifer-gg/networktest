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
    public BasicResponse<String> test(int id){
        return new BasicResponse(ResponseStatus.STATUS_SUCCESS,scriptFileMapper.test(id));
    }

    //交互式指令执行
    @PostMapping("/execute")
    public BasicResponse<String> executeCommand(@RequestParam("directive")String directive){
        try{
            String s = networkService.executeCommand(directive);
            return new BasicResponse<String>(ResponseStatus.STATUS_SUCCESS,s);
        }catch (Exception e){
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/getPortState")
    public BasicResponse<String> getPortState(@RequestParam("portId")String portId){
        try{
            String portState = networkService.getPortState(portId);
            return new BasicResponse<String>(ResponseStatus.STATUS_SUCCESS,portState);
        }catch (Exception e){
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

}
