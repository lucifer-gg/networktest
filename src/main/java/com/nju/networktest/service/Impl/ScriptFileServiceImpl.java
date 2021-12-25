package com.nju.networktest.service.Impl;

import com.nju.networktest.entity.CommandList;
import com.nju.networktest.entity.ConsoleOutput;
import com.nju.networktest.entity.ScriptFile;
import com.nju.networktest.entity.po.ScriptFilePO;
import com.nju.networktest.entity.vo.ExecuteScriptFileVO;
import com.nju.networktest.mapper.ScriptFileMapper;
import com.nju.networktest.service.ScriptFileService;
import com.nju.networktest.utils.TelnetConnect;
import com.nju.networktest.utils.telnetClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ScriptFile getConfigureScripts(String scriptFileName) {
        List<ScriptFilePO> scriptFilePOs = scriptFileMapper.getScriptFile(scriptFileName);
        if(scriptFilePOs.size()==0)return new ScriptFile();
        ScriptFilePO scriptFilePO=scriptFilePOs.get(0);
        List<String> commandsForrouterA = scriptFileMapper.getCommands(scriptFilePO.getId(), "routerA");
        List<String> commandsForrouterB = scriptFileMapper.getCommands(scriptFilePO.getId(), "routerB");
        List<String> commandsForrouterC = scriptFileMapper.getCommands(scriptFilePO.getId(), "routerC");
        CommandList commandList=new CommandList(commandsForrouterA,commandsForrouterB,commandsForrouterC);
        ScriptFile scriptFile=new ScriptFile(scriptFilePO,commandList);
        return scriptFile;
    }

//    @Override
//    public ExecuteScriptFileVO executeConf(String scriptFileName) {
//        ExecuteScriptFileVO executeScriptFileVO=new ExecuteScriptFileVO();
//        ScriptFile configureScriptFile = getConfigureScripts(scriptFileName);
//        executeScriptFileVO.setScriptFile(configureScriptFile);
//        StringBuilder sb=new StringBuilder();
//        ConsoleOutput consoleOutput=new ConsoleOutput();
//        executeScriptFileVO.setConsoleOutput(consoleOutput);
//
//        telnetClient telnetClientToRouterA = telnetConnect.getTelnetClientByName("routerA");
//        List<String> routerACommandList = configureScriptFile.getCommand().getRouterA();
//        for (String command:routerACommandList){
//            String res = telnetClientToRouterA.sendCommand(command);
//            sb.append(res+"\n");
//        }
//        consoleOutput.setRouterA(sb.toString());
//
//        sb=new StringBuilder();
//        telnetClient telnetClientToRouterB = telnetConnect.getTelnetClientByName("routerB");
//        List<String> routerBCommandList = configureScriptFile.getCommand().getRouterB();
//        for (String command:routerBCommandList){
//            String res = telnetClientToRouterB.sendCommand(command);
//            sb.append(res+"\n");
//        }
//        consoleOutput.setRouterB(sb.toString());
//
//        sb=new StringBuilder();
//        telnetClient telnetClientToRouterC = telnetConnect.getTelnetClientByName("routerC");
//        List<String> routerCCommandList = configureScriptFile.getCommand().getRouterC();
//        for (String command:routerCCommandList){
//            String res = telnetClientToRouterC.sendCommand(command);
//            sb.append(res+"\n");
//        }
//        consoleOutput.setRouterC(sb.toString());
//        return executeScriptFileVO;
//    }

    @Override
    public ExecuteScriptFileVO executeConf(String scriptFileName) {
        ExecuteScriptFileVO executeScriptFileVO=new ExecuteScriptFileVO();
        ScriptFile configureScriptFile = getConfigureScripts(scriptFileName);
        executeScriptFileVO.setScriptFile(configureScriptFile);

        telnetClient telnetClientToRouterA = telnetConnect.getTelnetClientByName("routerA");
        List<String> routerACommandList = configureScriptFile.getCommand().getRouterA();
        for (String command:routerACommandList){
            telnetClientToRouterA.write(command);
        }

        telnetClient telnetClientToRouterB = telnetConnect.getTelnetClientByName("routerB");
        List<String> routerBCommandList = configureScriptFile.getCommand().getRouterB();
        for (String command:routerBCommandList){
            telnetClientToRouterB.write(command);
        }

        telnetClient telnetClientToRouterC = telnetConnect.getTelnetClientByName("routerC");
        List<String> routerCCommandList = configureScriptFile.getCommand().getRouterC();
        for (String command:routerCCommandList){
            telnetClientToRouterC.write(command);
        }
        return executeScriptFileVO;
    }


}
