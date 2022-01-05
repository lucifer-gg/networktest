package com.nju.networktest.service;

import com.nju.networktest.entity.ScriptFile;
import com.nju.networktest.entity.TestResult;
import com.nju.networktest.entity.TestScript;
import com.nju.networktest.entity.vo.ExecuteScriptFileVO;

import java.util.List;


public interface ScriptFileService {
    boolean saveConfigureScripts(ScriptFile scriptFile);
    ScriptFile getConfigureScripts(String scriptFileName);
    ExecuteScriptFileVO executeConf(String scriptFileName);
    boolean deleteConfigureScripts(String scriptFileName);
    boolean updateConfigureScripts(ScriptFile scriptFile);
    List<String> getAllScriptFileName();
    TestResult executeTest(TestScript testScript);



}
