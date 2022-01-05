package com.nju.networktest.service.Impl;

import com.nju.networktest.entity.*;
import com.nju.networktest.entity.po.ScriptFilePO;
import com.nju.networktest.entity.vo.ExecuteScriptFileVO;
import com.nju.networktest.mapper.ScriptFileMapper;
import com.nju.networktest.service.ScriptFileService;
import com.nju.networktest.utils.TelnetConnect;
import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScriptFileServiceImpl implements ScriptFileService {
    @Autowired
    ScriptFileMapper scriptFileMapper;
    @Autowired
    TelnetConnect telnetConnect;

    @Override
    public boolean saveConfigureScripts(ScriptFile scriptFile) {
        try{
            List<String> commandListForRouterA=scriptFile.getCommand().getRouterA();
            List<String> commandListForRouterB=scriptFile.getCommand().getRouterB();
            List<String> commandListForRouterC=scriptFile.getCommand().getRouterC();
            ScriptFilePO scriptFilePO = new ScriptFilePO(scriptFile.getFileName(), scriptFile.getUselessInfo());
            scriptFileMapper.insertScriptFile(scriptFilePO);
            Integer fileId=scriptFilePO.getId();
            for(int i=0;i<commandListForRouterA.size();i++){
                scriptFileMapper.insertScriptCommand(fileId,"routerA",i+1,commandListForRouterA.get(i));
            }
            for(int i=0;i<commandListForRouterB.size();i++){
                scriptFileMapper.insertScriptCommand(fileId,"routerB",i+1,commandListForRouterB.get(i));
            }
            for(int i=0;i<commandListForRouterC.size();i++){
                scriptFileMapper.insertScriptCommand(fileId,"routerC",i+1,commandListForRouterC.get(i));
            }
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


    @Override
    public boolean deleteConfigureScripts(String scriptFileName) {
        List<Integer> fileIdByName = scriptFileMapper.getFileIdByName(scriptFileName);
        if(fileIdByName.size()==0)return false;
        scriptFileMapper.deleteScriptFile(scriptFileName);
        scriptFileMapper.deleteScriptCommands(fileIdByName.get(0));
        return true;
    }

    @Override
    public boolean updateConfigureScripts(ScriptFile scriptFile) {
        List<Integer> fileIdByName = scriptFileMapper.getFileIdByName(scriptFile.getFileName());
        if(fileIdByName.size()==0){
            saveConfigureScripts(scriptFile);
        }else {
            deleteConfigureScripts(scriptFile.getFileName());
            saveConfigureScripts(scriptFile);
        }


        return true;
    }

    @Override
    public List<String> getAllScriptFileName() {
        return scriptFileMapper.getAllScriptFileName();

    }

    @Override
    public TestResult executeTest(TestScript testScript) {
        Map<String,String> map=new HashMap<>();


//        telnetClient routerA = telnetConnect.getTelnetClientByName("routerA");
//        telnetClient routerB = telnetConnect.getTelnetClientByName("routerB");
//        telnetClient routerC = telnetConnect.getTelnetClientByName("routerC");


        telnetClient routerA = new telnetClient("VT220","#");
        telnetClient routerB = new telnetClient("VT220","#");
        telnetClient routerC = new telnetClient("VT220","#");
        if(!routerA.login(telnetConnect.getIpByName("routerA"), 23, "cisco")){
            System.out.println("与路由器A建立连接失效");
        }
        if(!routerB.login(telnetConnect.getIpByName("routerB"), 23, "cisco")){
            System.out.println("与路由器B建立连接失效");
        }
        if(!routerC.login(telnetConnect.getIpByName("routerC"), 23, "cisco")){
            System.out.println("与路由器C建立连接失效");
        }


        TestResult testResult=new TestResult(testScript);
        List<TestResultItem> testA1 = testResult.getRouterA();
        for(TestResultItem testResultItem:testA1){
            String s = routerA.sendCommand(testResultItem.getCommand());
            testResultItem.setOutput(s);
            testResultItem.setFlag(s.contains(map.get(testResultItem.getType())));

        }

        List<TestResultItem> testB1 = testResult.getRouterB();
        for(TestResultItem testResultItem:testB1){
            String s = routerB.sendCommand(testResultItem.getCommand());
            testResultItem.setOutput(s);
            testResultItem.setFlag(s.contains(map.get(testResultItem.getType())));

        }

        List<TestResultItem> testC1 = testResult.getRouterC();
        for(TestResultItem testResultItem:testC1){
            String s = routerC.sendCommand(testResultItem.getCommand());
            testResultItem.setOutput(s);
            testResultItem.setFlag(s.contains(map.get(testResultItem.getType())));
        }

        routerA.distinct();
        routerB.distinct();
        routerC.distinct();

        return testResult;


    }

    @Override
    public ScriptFile getConfigureScripts(String scriptFileName) {
        List<ScriptFilePO> scriptFilePOs = scriptFileMapper.getScriptFile(scriptFileName);

        if(scriptFilePOs.size()==0){
            return new ScriptFile();
        }
        ScriptFilePO scriptFilePO=scriptFilePOs.get(0);
        List<String> commandsForrouterA = scriptFileMapper.getCommands(scriptFilePO.getId(), "routerA");
        List<String> commandsForrouterB = scriptFileMapper.getCommands(scriptFilePO.getId(), "routerB");
        List<String> commandsForrouterC = scriptFileMapper.getCommands(scriptFilePO.getId(), "routerC");
        CommandList commandList=new CommandList(commandsForrouterA,commandsForrouterB,commandsForrouterC);

        ScriptFile scriptFile=new ScriptFile(scriptFilePO,commandList);
        return scriptFile;
    }

    @Override
    public ExecuteScriptFileVO executeConf(String scriptFileName) {
        telnetClient telnetA = new telnetClient("VT220","#");
        telnetClient telnetB = new telnetClient("VT220","#");
        telnetClient telnetC = new telnetClient("VT220","#");
        if(!telnetA.login(telnetConnect.getIpByName("routerA"), 23, "cisco")){
            System.out.println("与路由器A建立连接失效");
        }
        if(!telnetB.login(telnetConnect.getIpByName("routerB"), 23, "cisco")){
            System.out.println("与路由器B建立连接失效");
        }
        if(!telnetC.login(telnetConnect.getIpByName("routerC"), 23, "cisco")){
            System.out.println("与路由器C建立连接失效");
        }



        ExecuteScriptFileVO executeScriptFileVO=new ExecuteScriptFileVO();
        ScriptFile configureScriptFile = getConfigureScripts(scriptFileName);
        executeScriptFileVO.setScriptFile(configureScriptFile);
        StringBuilder sb=new StringBuilder();
        ConsoleOutput consoleOutput=new ConsoleOutput();
        executeScriptFileVO.setConsoleOutput(consoleOutput);



//        telnetClient telnetA = telnetConnect.getTelnetClientByName("routerA");
        List<String> routerACommandList = configureScriptFile.getCommand().getRouterA();
        System.out.println(routerACommandList.size());
        for (String command:routerACommandList){
            String res = telnetA.sendCommand(command);
//            System.out.println("---command---");
//            System.out.println(command);
//            System.out.println("---res------");
//            System.out.println(res);
//            System.out.println("--end--");
            sb.append(res+"\n");
        }
        consoleOutput.setRouterA(sb.toString());

        sb=new StringBuilder();
//        telnetClient telnetB = telnetConnect.getTelnetClientByName("routerB");
        List<String> routerBCommandList = configureScriptFile.getCommand().getRouterB();
        System.out.println(routerBCommandList.size());
        for (String command:routerBCommandList){
            String res = telnetB.sendCommand(command);
//            System.out.println("---command---");
//            System.out.println(command);
//            System.out.println("---res------");
//            System.out.println(res);
//            System.out.println("--end--");
            sb.append(res+"\n");
        }
        consoleOutput.setRouterB(sb.toString());

        sb=new StringBuilder();
//        telnetClient telnetC = telnetConnect.getTelnetClientByName("routerC");
        List<String> routerCCommandList = configureScriptFile.getCommand().getRouterC();
        System.out.println(routerCCommandList.size());
        for (String command:routerCCommandList){
            String res = telnetC.sendCommand(command);
//            System.out.println("---command---");
//            System.out.println(command);
//            System.out.println("---res------");
//            System.out.println(res);
//            System.out.println("--end--");
            sb.append(res+"\n");
        }
        consoleOutput.setRouterC(sb.toString());

        telnetA.distinct();
        telnetB.distinct();
        telnetC.distinct();

        return executeScriptFileVO;
    }

//    @Override
//    public ExecuteScriptFileVO executeConf(String scriptFileName) {
//        ExecuteScriptFileVO executeScriptFileVO=new ExecuteScriptFileVO();
//        ScriptFile configureScriptFile = getConfigureScripts(scriptFileName);
//        executeScriptFileVO.setScriptFile(configureScriptFile);
//
//        telnetClient telnetClientToRouterA = telnetConnect.getTelnetClientByName("routerA");
//        List<String> routerACommandList = configureScriptFile.getCommand().getRouterA();
//        for (String command:routerACommandList){
//            telnetClientToRouterA.write(command);
//        }
//
//        telnetClient telnetClientToRouterB = telnetConnect.getTelnetClientByName("routerB");
//        List<String> routerBCommandList = configureScriptFile.getCommand().getRouterB();
//        for (String command:routerBCommandList){
//            telnetClientToRouterB.write(command);
//        }
//
////        telnetClient telnetClientToRouterC = telnetConnect.getTelnetClientByName("routerC");
////        List<String> routerCCommandList = configureScriptFile.getCommand().getRouterC();
////        for (String command:routerCCommandList){
////            telnetClientToRouterC.write(command);
////        }
//        return executeScriptFileVO;
//    }


}
