package com.nju.networktest.controller;

import com.nju.networktest.entity.PortState;
import com.nju.networktest.entity.vo.BasicResponse;
import com.nju.networktest.entity.vo.ResponseStatus;
import com.nju.networktest.mapper.ScriptFileMapper;
import com.nju.networktest.service.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/reload")
    public BasicResponse reload(@RequestParam("hostName")String hostName){
        try{
            if("all".equals(hostName)){
                return new BasicResponse(ResponseStatus.STATUS_SUCCESS,networkService.reloadAll());
            }else {
                return new BasicResponse(ResponseStatus.STATUS_SUCCESS,networkService.reload(hostName));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/getPortState")
    public BasicResponse getPortState(@RequestParam("portId")String portId,@RequestParam("hostName")String hostName){
        try{
            PortState state = networkService.getPortState(portId,hostName);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,state);
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/establishConnect")
    public BasicResponse establishConnect(){
        try{
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,networkService.establishConnect());
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }

    }

    @GetMapping("/flashAll")
    public BasicResponse flashAll(){
        try {
            networkService.flashAll();
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,true);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/updatePortAddress")
    public BasicResponse updatePortAddress(@RequestParam("hostIp")String hostIp,@RequestParam("portId") String portId,@RequestParam("newPortIp") String newPortIp,@RequestParam("newMask") String newMask){
        try {
            boolean b = networkService.upDatePortAddress(hostIp, portId, newPortIp, newMask);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,b);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @GetMapping("/getRouterState")
    public BasicResponse getRouteState(@RequestParam("hostName")String hostName){

        try {
            List<PortState> routerState = networkService.getRouterState(hostName);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,routerState);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }


}
