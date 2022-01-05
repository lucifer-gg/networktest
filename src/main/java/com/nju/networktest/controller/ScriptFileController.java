package com.nju.networktest.controller;

import com.nju.networktest.entity.ScriptFile;
import com.nju.networktest.entity.TestResult;
import com.nju.networktest.entity.TestScript;
import com.nju.networktest.entity.vo.BasicResponse;
import com.nju.networktest.entity.vo.ExecuteScriptFileVO;
import com.nju.networktest.entity.vo.ResponseStatus;
import com.nju.networktest.service.ScriptFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ScriptFileController {
    @Autowired
    ScriptFileService scriptFileService;

    //保存脚本文件
    @PostMapping("/saveConf")
    public BasicResponse saveConfigureScripts(@RequestBody ScriptFile scriptFile){
        try {
            boolean res = scriptFileService.saveConfigureScripts(scriptFile);
            if(res){
                return new BasicResponse(ResponseStatus.STATUS_SUCCESS);
            }else {
                return new BasicResponse(ResponseStatus.SERVER_ERROR,"保存脚本失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    //获取脚本文件内容
    @GetMapping("/getConf")
    public BasicResponse getConf(@RequestParam("fileName") String fileName){
        try {
            ScriptFile configureScripts = scriptFileService.getConfigureScripts(fileName);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,configureScripts);
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    //删除脚本文件
    @GetMapping("/deleteConf")
    public BasicResponse deleteConf(@RequestParam("fileName")String fileName){
        try {
            boolean b = scriptFileService.deleteConfigureScripts(fileName);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,b);
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    //更新脚本文件，参数和save一致
    @PostMapping("/updateConf")
    public BasicResponse updateConf(@RequestBody ScriptFile scriptFile){
        try {
            boolean b = scriptFileService.updateConfigureScripts(scriptFile);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,b);
        }catch (Exception e){
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }

    }

    //一键执行脚本文件
    @GetMapping("/executeConf")
    public BasicResponse executeConf(@RequestParam("fileName")String fileName){
        try {
            ExecuteScriptFileVO executeScriptFileVO = scriptFileService.executeConf(fileName);
            //暂时只返回脚本
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,executeScriptFileVO.getConsoleOutput());
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    //返回所有脚本文件的名字
    @GetMapping("/getAllScriptFileName")
    public BasicResponse getAllScriptFileName(){
        try {
            List<String> allScriptFileName = scriptFileService.getAllScriptFileName();
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,allScriptFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    @PostMapping("/executeTest")
    public BasicResponse executeTest(@RequestBody TestScript testScript){
        try {
            TestResult testResult = scriptFileService.executeTest(testScript);
            return new BasicResponse(ResponseStatus.STATUS_SUCCESS,testResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }

    }


}
