package com.nju.networktest.controller;

import com.nju.networktest.service.ScriptFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ScriptFileController {
    @Autowired
    ScriptFileService scriptFileService;




}
